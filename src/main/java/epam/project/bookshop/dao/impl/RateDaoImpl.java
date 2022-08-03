package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.RateDao;
import epam.project.bookshop.dto.RateDto;
import epam.project.bookshop.entity.Rate;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.mapper.RateMapper;
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

import static epam.project.bookshop.command.ParameterName.ID;
import static epam.project.bookshop.dao.SQLFragments.*;

public class RateDaoImpl implements RateDao {

    private static final Logger logger = LogManager.getLogger();
    private static final RateDaoImpl instance = new RateDaoImpl();

    private static final String INSERT_RATE = "INSERT INTO rate_book(book_id, user_id, rate) VALUES (?, ?, ?) RETURNING id;";
    private static final String AVERAGE_RATE_BY_BOOK_ID = "SELECT avg(rate) FROM rate_book WHERE book_id =? and deleted=false";
    private static final String SUM_OF_VOTED_USERS = "SELECT count(user_id) FROM rate_book WHERE book_id = ? and deleted=false";
    private static final String DELETE_RATES_BY_BOOK_ID = "UPDATE rate_book SET deleted = true WHERE book_id = ? and deleted=false";
    private static final String RATE_BY_BOOK_AND_USER_ID = "SELECT id, book_id, user_id, rate FROM rate_book WHERE book_id=? and user_id=? and deleted=false";
    private static final String UPDATE_RATE = "UPDATE rate_book SET rate = ? WHERE id = ? and deleted=false";
    private static final String SELECT_ALL_RATE = "SELECT id, book_id, user_id, rate FROM rate_book WHERE deleted=false";
    private static final String SELECT_RATE_BY_ID = "SELECT id, book_id, user_id, rate FROM rate_book WHERE id = ? and deleted=false";
    private static final String DELETE_RATE_BY_ID = "UPDATE rate_book SET deleted=true WHERE id = ? and deleted=false";

    public static RateDaoImpl getInstance() {
        return instance;
    }

    private RateDaoImpl() {
    }

    @Override
    public Long save(Rate rate) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_RATE)) {

            statement.setLong(1, rate.getBookId());
            statement.setLong(2, rate.getUserId());
            statement.setLong(3, rate.getRate());

            ResultSet resultSet = statement.executeQuery();
            long id = 0L;

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
    public boolean updated(String rateQuantity, Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RATE)) {

            statement.setLong(1, Long.parseLong(rateQuantity));
            statement.setLong(2, id);

            return statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RATE_BY_ID)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<RateDto> findById(Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_RATE_BY_ID)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            RateDto rateDto = new RateDto();
            while (resultSet.next()) {
                rateDto = RateMapper.getInstance().resultSetToDto(resultSet);
            }

            if (rateDto.getId() != null) {
                return Optional.of(rateDto);
            } else return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<RateDto> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_RATE)) {

            ResultSet resultSet = statement.executeQuery();

            List<RateDto> rateDtoList = new ArrayList<>();

            while (resultSet.next()) {
                RateDto rateDto = RateMapper.getInstance().resultSetToDto(resultSet);
                rateDtoList.add(rateDto);
            }

            return rateDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Double> getAverageRateByBookId(Long bookId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(AVERAGE_RATE_BY_BOOK_ID)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            double average_rate = 0.0;

            while (resultSet.next()) {
                average_rate = resultSet.getDouble(AVERAGE);
            }

            return Optional.of(average_rate);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<Long> getNumberOfVotedUsersByBookId(Long bookId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SUM_OF_VOTED_USERS)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            long numberOfUsers = 0L;

            while (resultSet.next()) {
                numberOfUsers = resultSet.getLong(COUNT);
            }

            return Optional.of(numberOfUsers);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteByBookId(Long bookId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RATES_BY_BOOK_ID)) {
            statement.setLong(1, bookId);
            statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<RateDto> findRateByBookIdAndUserId(Long bookId, Long userId) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(RATE_BY_BOOK_AND_USER_ID)) {

            statement.setLong(1, bookId);
            statement.setLong(2, userId);

            ResultSet resultSet = statement.executeQuery();

            RateDto rateDto = new RateDto();
            while (resultSet.next()) {
                rateDto = RateMapper.getInstance().resultSetToDto(resultSet);
            }

            if (rateDto.getId() != null){
                return Optional.of(rateDto);
            }else  return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

    }
}
