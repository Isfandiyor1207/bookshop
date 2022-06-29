package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.AuthorDao;
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

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDaoImpl implements AuthorDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_AUTHOR_NAME = "SELECT id, fio FROM author WHERE fio = ? AND deleted = false";
    private static final String SELECT_BY_ID = "SELECT id, fio FROM author WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL = "SELECT id, fio FROM author WHERE deleted = false order by id";
    private static final String DELETE_AUTHOR_BY_ID = "UPDATE author SET deleted = true WHERE id =? AND deleted = false";
    private static final String UPDATE_AUTHOR_BY_ID = "UPDATE author SET fio = ?, updated_time = now() WHERE id = ? AND deleted = false";
    private static final String INSERT_AUTHOR = "INSERT INTO author(fio) VALUES (?)";
    private static AuthorDaoImpl instance;

    public static AuthorDaoImpl getInstance() {
        if (instance == null) {
            instance = new AuthorDaoImpl();
        }
        return instance;
    }


    @Override
    public boolean save(Author author) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR)) {

            statement.setString(1, author.getFio().toLowerCase());
            int numberOfRow = statement.executeUpdate();

            return numberOfRow > 0;
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
    public Optional<Author> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            Author author = new Author();
            while (resultSet.next()) {
                author = AuthorMapper.getInstance().resultSetToEntity(resultSet);
            }

            if (author.getFio() != null) {
                return Optional.of(author);
            } else return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() throws DaoException {
        List<Author> authorList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                authorList.add(AuthorMapper.getInstance().resultSetToEntity(resultSet));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return authorList;
    }

    @Override
    public Optional<Author> findByFio(String fio) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_AUTHOR_NAME)) {

            statement.setString(1, fio.toLowerCase());

            ResultSet resultSet = statement.executeQuery();

            Author author=new Author();

            while (resultSet.next()){
                author = AuthorMapper.getInstance().resultSetToEntity(resultSet);
            }

            if (author.getFio() != null) {
                return Optional.of(author);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
