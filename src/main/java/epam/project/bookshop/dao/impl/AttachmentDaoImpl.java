package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.AttachmentDao;
import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.mapper.AttachmentMapper;
import epam.project.bookshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttachmentDaoImpl implements AttachmentDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_ALL_BY_ID = "SELECT id, absolute_name, upload_path, hash_name, extension FROM attachment WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL = "SELECT id, fio FROM author WHERE deleted = false order by id";
    private static final String SELECT_ALL_BY_BOOK_ID = "SELECT attachment_id FROM attachment_book_list WHERE book_id = ?";
    private static final String DELETE_ATTACHMENT_BY_ID = "UPDATE attachment SET deleted = true WHERE id =? AND deleted = false";
    private static final String DELETE_ATTACHMENT_BY_BOOK_ID = "DELETE FROM attachment_book_list WHERE book_id = ?";
    private static final String UPDATE_ATTACHMENT_BY_ID = "UPDATE author SET fio = ?, updated_time = now() WHERE id = ? AND deleted = false";
    private static final String INSERT_ATTACHMENT = "INSERT INTO attachment(absolute_name, extension, content_type, file_size, upload_path) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String ATTACH_BOOK_TO_ATTACHMENT = "INSERT INTO attachment_book_list(attachment_id, book_id) VALUES (?, ?)";
    private static final AttachmentDaoImpl instance = new AttachmentDaoImpl();

    public static AttachmentDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(Attachment attachment) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ATTACHMENT)) {

            statement.setString(1, attachment.getAbsoluteName());
            statement.setString(2, attachment.getExtension());
            statement.setString(3, attachment.getContentType());
            statement.setLong(4, attachment.getFileSize());
            statement.setString(5, attachment.getUploadPath());
            ResultSet resultSet = statement.executeQuery();

            Long id = null;
            while (resultSet.next()) {
                id = resultSet.getLong("id");
            }

            return id;

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

    @Override
    public boolean updated(String query, Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT_BY_ID)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<AttachmentDto> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<AttachmentDto> findAll() throws DaoException {
        return null;
    }

    @Override
    public void attachFileToBook(Long bookId, Long fileId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ATTACH_BOOK_TO_ATTACHMENT)) {

            statement.setLong(1, fileId);
            statement.setLong(2, bookId);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findAllAttachmentIdByBookId(Long bookId) throws DaoException {
        List<Long> listOfFile = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_BOOK_ID)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("attachment_id");

                listOfFile.add(id);
            }
            return listOfFile;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<AttachmentDto> findAllByBookId(Long bookId) throws DaoException {
        List<Long> attachmentIdList = findAllAttachmentIdByBookId(bookId);
        List<AttachmentDto> attachmentDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID)) {

            for (Long attachmentId : attachmentIdList) {

                statement.setLong(1, attachmentId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    attachmentDtoList.add(AttachmentMapper.getInstance().resultSetToDto(resultSet));
                }
            }

            return attachmentDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAttachedFile(Long bookId, Long attachmentId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ATTACHMENT_BY_BOOK_ID)) {

            statement.setLong(1, bookId);

            statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
