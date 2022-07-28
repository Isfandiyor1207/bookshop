package epam.project.bookshop.dao.impl;

import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.dao.GenreDao;
import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.mapper.GenreMapper;
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
public class GenreDaoImpl implements GenreDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_GENRE_NAME = "SELECT id, name FROM genre WHERE name = ? AND deleted = false";
    private static final String SELECT_BY_ID = "SELECT id, name FROM genre WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL = "SELECT id, name FROM genre WHERE deleted = false order by id";
    private static final String SELECT_ALL_GENRE_ID_BY_BOOK_ID = "SELECT genre_id FROM genre_book_list WHERE book_id = ?";
    private static final String DELETE_GENRE_BY_ID = "UPDATE genre SET deleted = true WHERE id =? AND deleted = false";
    private static final String DELETE_LIST_OF_BOOK_GENRE = "DELETE FROM genre_book_list WHERE book_id =?";
    private static final String SELECT_ALL_BOOK_ID = "SELECT book_id FROM genre_book_list WHERE genre_id =?";
    private static final String UPDATE_GENRE_BY_ID = "UPDATE genre SET name = ?, updated_time = now() WHERE id =? AND deleted = false";
    private static final String INSERT_GENRE = "INSERT INTO genre(name) VALUES (?) RETURNING id;";
    private static final String ATTACH_BOOK_TO_GENRE = "INSERT INTO genre_book_list(genre_id, book_id) VALUES (?, ?)";

    private static final GenreDaoImpl instance = new GenreDaoImpl();

    private GenreDaoImpl() {
    }

    public static GenreDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(Genre genre) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_GENRE)) {

            statement.setString(1, genre.getName().toLowerCase());
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
    public boolean updated(String genre, Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GENRE_BY_ID)) {

            statement.setString(1, genre.toLowerCase());
            statement.setLong(2, id);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;

        } catch (SQLException e) {
            logger.error("Genre does not updated by this id: " + id);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_GENRE_BY_ID)) {

            statement.setLong(1, id);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;

        } catch (SQLException e) {
            logger.error("Genre does not deleted bi this id: " + id);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<GenreDto> findById(Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            GenreDto genreDto = new GenreDto();
            while (resultSet.next()) {
                genreDto = GenreMapper.getInstance().resultSetToDto(resultSet);
            }

            if (genreDto.getName() != null) {
                return Optional.of(genreDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.info("Genre not found!");
            throw new DaoException(e);
        }
    }

    @Override
    public List<GenreDto> findAll() throws DaoException {
        List<GenreDto> genreList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                genreList.add(GenreMapper.getInstance().resultSetToDto(resultSet));
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return genreList;
    }

    @Override
    public Optional<GenreDto> findByName(String name) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_GENRE_NAME)) {

            statement.setObject(1, name.toLowerCase());

            ResultSet resultSet = statement.executeQuery();
            GenreDto genreDto = new GenreDto();

            while (resultSet.next()) {
                genreDto = GenreMapper.getInstance().resultSetToDto(resultSet);
            }
            logger.info(genreDto);

            if (genreDto.getName() != null) {
                return Optional.of(genreDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.info("Genre not found!");
            throw new DaoException(e);
        }
    }

    @Override
    public void attachBookToGenre(Long bookId, Long genreId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ATTACH_BOOK_TO_GENRE)) {

            statement.setLong(1, genreId);
            statement.setLong(2, bookId);

            statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

    @Override
    public List<GenreDto> findAllByBookId(Long bookId) throws DaoException {

        List<Long> genreIdList = findAllGenreIdByBookId(bookId);

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            List<GenreDto> genreDtoList = new ArrayList<>();

            for (Long genreId : genreIdList) {
                statement.setLong(1, genreId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    genreDtoList.add(GenreMapper.getInstance().resultSetToDto(resultSet));
                }

            }
            return genreDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findAllGenreIdByBookId(Long bookId) throws DaoException {

        List<Long> genreIdList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_GENRE_ID_BY_BOOK_ID)) {

            statement.setLong(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                genreIdList.add(resultSet.getLong("genre_id"));
            }

            return genreIdList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAttachedGenre(Long bookId, Long genreId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_LIST_OF_BOOK_GENRE)) {

            statement.setLong(1, bookId);

            statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Long> findAllBookIdByGenreId(Long genreId) throws DaoException {

        List<Long> bookIdList=new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOK_ID)) {

            statement.setLong(1, genreId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                bookIdList.add(resultSet.getLong(BOOK_ID));
            }

            return bookIdList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

}
