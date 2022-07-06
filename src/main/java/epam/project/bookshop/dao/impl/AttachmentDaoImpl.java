package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.AttachmentDao;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AttachmentDaoImpl implements AttachmentDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_ID = "SELECT id, fio FROM author WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL = "SELECT id, fio FROM author WHERE deleted = false order by id";
    private static final String DELETE_ATTACHMENT_BY_ID = "UPDATE author SET deleted = true WHERE id =? AND deleted = false";
    private static final String UPDATE_ATTACHMENT_BY_ID = "UPDATE author SET fio = ?, updated_time = now() WHERE id = ? AND deleted = false";
    private static final String INSERT_ATTACHMENT = "INSERT INTO attachment(absolute_name, extension, content_type, file_size, upload_path) VALUES (?, ?, ?, ?, ?)";
    private static final String ATTACH_BOOK_TO_ATTACHMENT = "INSERT INTO author_book_list(author_id, book_id) VALUES (?, ?)";
    private static final AttachmentDaoImpl instance = new AttachmentDaoImpl();

    public static AttachmentDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean save(Attachment attachment) throws DaoException {

        try (Connection connection= ConnectionPool.getInstance().getConnection();
             PreparedStatement statement=connection.prepareStatement(INSERT_ATTACHMENT)) {

            statement.setString(1, attachment.getAbsoluteName());
            statement.setString(2, attachment.getExtension());
            statement.setString(3, attachment.getContentType());
            statement.setLong(4, attachment.getFileSize());
            statement.setString(5, attachment.getUploadPath());

            return statement.execute();

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
    public boolean deleteById(Long id) throws DaoException, ServiceException {
        return false;
    }

    @Override
    public Optional<epam.project.bookshop.entity.Attachment> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<epam.project.bookshop.entity.Attachment> findAll() throws DaoException {
        return null;
    }
}
