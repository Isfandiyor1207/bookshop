package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.BookDao;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.mapper.BookMapper;
import epam.project.bookshop.pool.ConnectionPool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDaoImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_BOOK = "SELECT id, firstname, lastname, password, phoneNumber, email, username, roleid FROM users WHERE username = ? AND deleted = false";
    private static final String SELECT_BOOK_BY_NAME = "SELECT id FROM book WHERE name = ? AND deleted = false";
    private static final String SELECT_BY_ID = "SELECT id, name, isbn, publisher, publishing_year, genre_id, price, total FROM book WHERE  id = ? AND deleted = false";
    private static final String SELECT_BY_ISBN = "SELECT id, name, isbn, publisher, publishing_year, genre_id, price, total FROM book WHERE isbn = ? and deleted=false";
    private static final String SELECT_ALL = "SELECT id, name, isbn, publisher, publishing_year, genre_id, price, total FROM book WHERE deleted=false order by id";
    private static final String DELETE_BOOK_BY_ID = "UPDATE book SET deleted = true WHERE id =? AND deleted = false";
    private static final String UPDATE_BOOK_BY_ID = "UPDATE users SET %s WHERE id =%s, updated_time = now() AND deleted = false";
    private static final String INSERT_BOOK = "INSERT INTO book(name, isbn, publisher, publishing_year, price, total, genre_id) VALUES (?, ?, ?, ?, ?, ?, ?);";

    static BookDaoImpl instance;

    public static BookDaoImpl getInstance() {
        if (instance == null) {
            instance = new BookDaoImpl();
        }
        return instance;
    }


    @Override
    public boolean save(Book book) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK)) {

            statement.setString(1, book.getName().toLowerCase());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getPublisher());
            statement.setLong(4, book.getPublishingYear());
            statement.setLong(5, book.getPrice());
            statement.setLong(6, book.getNumberOfBooks());
            statement.setLong(7, book.getGenreId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updated(String query, Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String formatQuery = String.format(UPDATE_BOOK_BY_ID, query, id);

            return statement.executeUpdate(formatQuery) > 0;

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_BY_ID)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            Book book = new Book();
            while (resultSet.next()) {
                book = BookMapper.getInstance().resultSetToEntity(resultSet);
            }

            if (book.getName() != null) {
                return Optional.of(book);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Book> findAll() throws DaoException {

        List<Book> bookList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();
            Book book;
            while (resultSet.next()) {
                book = BookMapper.getInstance().resultSetToEntity(resultSet);
                bookList.add(book);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return bookList;
    }

    @Override
    public Optional<Book> findByName(String name) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Long findIdByName(String name) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_NAME)) {

            statement.setString(1, name);

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
    public Optional<Book> findByISBN(String isbn) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN)) {

            statement.setString(1, isbn);

            ResultSet resultSet = statement.executeQuery();

            Book book = new Book();

            while (resultSet.next()) {
                book = BookMapper.getInstance().resultSetToEntity(resultSet);
            }

            if (book.getName() != null) {
                return Optional.of(book);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }
}
