package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.BookDao;
import epam.project.bookshop.dto.BookDto;
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

import static epam.project.bookshop.command.ParameterName.ID;
import static epam.project.bookshop.dao.SQLFragments.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDaoImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_BOOK = "SELECT id, firstname, lastname, password, phoneNumber, email, username, roleid FROM users WHERE username = ? AND deleted = false";
    private static final String SELECT_BOOK_ID_BY_NAME = "SELECT id FROM book WHERE name = ? AND deleted = false";
    private static final String SELECT_BOOK_BY_NAME = "SELECT id, name, isbn, publisher, publishing_year, price, total, description FROM book WHERE name = ? AND deleted = false";
    private static final String SELECT_ALL_BOOK_BY_NAME = "SELECT id, name, isbn, publisher, publishing_year, price, total, description FROM book WHERE name LIKE ?";
    private static final String SELECT_BY_ID = "SELECT id, name, isbn, publisher, publishing_year, price, total, description FROM book WHERE  id = ? AND deleted = false";
    private static final String SELECT_BY_ISBN = "SELECT id, name, isbn, publisher, publishing_year, price, total, description FROM book WHERE isbn = ? and deleted=false";
    private static final String SELECT_ALL = "SELECT id, name, isbn, publisher, publishing_year, price, total, description FROM book WHERE deleted=false order by id";
    private static final String DELETE_BOOK_BY_ID = "UPDATE book SET deleted = true WHERE id =? AND deleted = false";
    private static final String UPDATE_BOOK_QUANTITY = "UPDATE book SET total = ? WHERE id =? AND deleted = false";
    private static final String UPDATE_BOOK_BY_ID = "UPDATE book SET %s, updated_time = now() WHERE id =%s AND deleted = false";
    private static final String INSERT_BOOK = "INSERT INTO book(name, isbn, publisher, publishing_year, price, total, description) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;";
    private static final BookDaoImpl instance = new BookDaoImpl();

    private BookDaoImpl() {
    }

    public static BookDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(Book book) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOK)) {

            statement.setString(1, book.getName().toLowerCase());
            statement.setString(2, book.getIsbn());
            statement.setString(3, book.getPublisher());
            statement.setLong(4, book.getPublishingYear());
            statement.setLong(5, book.getPrice());
            statement.setLong(6, book.getNumberOfBooks());
            statement.setString(7, book.getDescription());

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
    public Optional<BookDto> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            BookDto bookDto = new BookDto();
            while (resultSet.next()) {
                bookDto = BookMapper.getInstance().resultSetToDto(resultSet);
            }

            if (bookDto.getName() != null) {
                return Optional.of(bookDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<BookDto> findAll() throws DaoException {

        List<BookDto> bookList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookDto bookDto = BookMapper.getInstance().resultSetToDto(resultSet);
                bookList.add(bookDto);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return bookList;
    }

    @Override
    public Optional<BookDto> findByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_BY_NAME)) {

            statement.setString(1, name.toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            BookDto book = new BookDto();

            while (resultSet.next()) {
                book = BookMapper.getInstance().resultSetToDto(resultSet);
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
    public Long findIdByName(String name) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOOK_ID_BY_NAME)) {

            statement.setString(1, name.toLowerCase());

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
    public Optional<BookDto> findByISBN(String isbn) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN)) {

            statement.setString(1, isbn);

            ResultSet resultSet = statement.executeQuery();

            BookDto book = new BookDto();

            while (resultSet.next()) {
                book = BookMapper.getInstance().resultSetToDto(resultSet);
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
    public List<BookDto> findAllBookByBookName(String bookName) throws DaoException {

        List<BookDto> bookDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOK_BY_NAME)) {

            statement.setString(1, PERCENTAGE + bookName.toLowerCase() + PERCENTAGE);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                bookDtoList.add(BookMapper.getInstance().resultSetToDto(resultSet));
            }

            return bookDtoList;

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void changeQuantityByBookId(Long bookId, Long bookQuantity) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUANTITY)) {

            statement.setLong(1, bookQuantity);
            statement.setLong(2, bookId);

            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
