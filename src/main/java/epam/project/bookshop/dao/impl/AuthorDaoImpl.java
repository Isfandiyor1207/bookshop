package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.AuthorDao;
import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.mapper.AuthorMapper;
import epam.project.bookshop.pool.ConnectionPool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDaoImpl implements AuthorDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_AUTHOR_NAME = "SELECT id, fio FROM author WHERE fio = ? AND deleted = false";
    private static final String SELECT_BY_AUTHOR_NAME_USING_LIKE = "SELECT id, fio FROM author WHERE fio like ? AND deleted = false";
    private static final String SELECT_BY_ID = "SELECT id, fio FROM author WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL_ID_BY_FIO = "SELECT id FROM author WHERE fio LIKE ?";
    private static final String SELECT_LIST_OF_AUTHOR_BY_BOOK_ID = "SELECT author_id FROM author_book_list WHERE  book_id = ?";
    private static final String SELECT_ALL = "SELECT id, fio FROM author WHERE deleted = false order by id";
    private static final String SELECT_ALL_BOOK_ID_BY_AUTHOR_ID = "SELECT book_id FROM author_book_list WHERE author_id = ?";
    private static final String DELETE_AUTHOR_BY_ID = "UPDATE author SET deleted = true WHERE id =? AND deleted = false";
    private static final String DELETE_LIST_OF_BOOK_AUTHOR = "DELETE FROM author_book_list WHERE book_id =?";
    private static final String UPDATE_AUTHOR_BY_ID = "UPDATE author SET fio = ?, updated_time = now() WHERE id = ? AND deleted = false";
    private static final String INSERT_AUTHOR = "INSERT INTO author(fio) VALUES (?) RETURNING id";
    private static final String ATTACH_BOOK_AUTHOR = "INSERT INTO author_book_list(author_id, book_id) VALUES (?, ?)";
    private static final AuthorDaoImpl instance = new AuthorDaoImpl();

    private AuthorDaoImpl() {
    }

    public static AuthorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(Author author) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR)) {

            statement.setString(1, author.getFio().trim().toLowerCase());
            ResultSet resultSet = statement.executeQuery();

            Long id = null;

            while (resultSet.next()) {
                id = resultSet.getLong(ID);
            }

            return id;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updated(String fio, Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR_BY_ID)) {

            statement.setString(1, fio.toLowerCase());
            statement.setLong(2, id);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;

        } catch (SQLException e) {
            logger.error("Author does not updated by this id: " + id);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AUTHOR_BY_ID)) {
            statement.setLong(1, id);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (SQLException e) {
            logger.error("Author not deleted: " + e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<AuthorDto> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            AuthorDto authorDto = new AuthorDto();
            while (resultSet.next()) {
                authorDto = AuthorMapper.getInstance().resultSetToDto(resultSet);
            }

            if (authorDto.getFio() != null) {
                return Optional.of(authorDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<AuthorDto> findAll() throws DaoException {
        List<AuthorDto> authorList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                authorList.add(AuthorMapper.getInstance().resultSetToDto(resultSet));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return authorList;
    }

    @Override
    public Optional<AuthorDto> findByFio(String fio) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_AUTHOR_NAME)) {

            statement.setString(1, fio.toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            AuthorDto authorDto = new AuthorDto();

            while (resultSet.next()) {
                authorDto = AuthorMapper.getInstance().resultSetToDto(resultSet);
            }

            if (authorDto.getFio() != null) {
                return Optional.of(authorDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void attachBookToAuthor(Long bookId, Long authorId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ATTACH_BOOK_AUTHOR)) {

            statement.setLong(1, authorId);
            statement.setLong(2, bookId);

            statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<AuthorDto> findAllAuthorByBookId(Long bookId) throws DaoException {

        List<AuthorDto> authorDtoList = new ArrayList<>();
        List<Long> listOfAuthorId = findListOfAuthorIdByBookId(bookId);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            for (Long authorId : listOfAuthorId) {
                statement.setLong(1, authorId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    authorDtoList.add(AuthorMapper.getInstance().resultSetToDto(resultSet));
                }
            }
            return authorDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

    @Override
    public List<Long> findListOfAuthorIdByBookId(Long bookId) throws DaoException {

        List<Long> listOfAuthorId = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_LIST_OF_AUTHOR_BY_BOOK_ID)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listOfAuthorId.add(resultSet.getLong(AUTHOR_ID));
            }

            return listOfAuthorId;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAttachedAuthor(Long bookId, Long authorId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_LIST_OF_BOOK_AUTHOR)) {

            statement.setLong(1, bookId);

            statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findAuthorIdByName(String authorName) throws DaoException {
        List<Long> authorIdList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ID_BY_FIO)) {

            statement.setString(1, "%" + authorName.toLowerCase() + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                authorIdList.add(resultSet.getLong(ID));
            }
            return authorIdList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findAllBookIdByAuthorId(Long authorId) throws DaoException {

        List<Long> bookIdList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOK_ID_BY_AUTHOR_ID)) {

            statement.setLong(1, authorId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookIdList.add(resultSet.getLong(BOOK_ID));
            }

            return bookIdList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<AuthorDto> findBySearchingFio(String fio) throws DaoException {
        List<AuthorDto> authorDtoList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_AUTHOR_NAME_USING_LIKE)) {

            statement.setString(1,  "%" + fio.toLowerCase() + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                authorDtoList.add(AuthorMapper.getInstance().resultSetToDto(resultSet));
            }

            return authorDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
