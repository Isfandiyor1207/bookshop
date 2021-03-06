package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.UserDao;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.mapper.UserMapper;
import epam.project.bookshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;

public class UserDaoImpl implements UserDao {

    public static final Logger logger = LogManager.getLogger();

    private static final String SELECT_BY_USERNAME = "SELECT id, firstname, lastname, password, phoneNumber, email, username, roleid FROM users WHERE username = ? AND deleted = false";
    private static final String SELECT_BY_EMAIL = "SELECT id, firstname, lastname, password, phoneNumber, email, username, roleid FROM users WHERE email = ? AND deleted = false";
    private static final String SELECT_BY_PHONE_NUMBER = "SELECT id, firstname, lastname, password, phoneNumber, email, username, roleid FROM users WHERE phoneNumber = ? AND deleted = false";
    private static final String SELECT_BY_ID = "SELECT id, firstname, lastname, phoneNumber, email, username, roleid, password FROM users WHERE  id = ? AND deleted = false";
    private static final String SELECT_ALL = "SELECT id, firstname, lastname, phoneNumber, email, username, roleid, password FROM  users WHERE deleted=false order by id";
    private static final String SELECT_ROLE_ID = "SELECT roleid FROM users WHERE username = ? AND deleted=false;";
    private static final String DELETE_USERS_BY_ID = "UPDATE users SET deleted = true WHERE id =? AND deleted = false";
    private static final String UPDATE_USERS_BY_ID = "UPDATE users SET %s updated_time = now() WHERE id =%s AND deleted = false";
    private static final String UPDATE_USERS_STATUS_BY_ID = "UPDATE users SET roleid = ?, updated_time = now() WHERE id = ? AND deleted = false";
    private static final String INSERT_USER = "INSERT INTO users(firstname, lastname, username, password, email, phoneNumber) VALUES (?, ?, ?, ?, ?, ?) RETURNING id;";
    private static final String SEARCHING_USER = "SELECT id, firstname, lastname, phoneNumber, email, password, username, roleid FROM users WHERE %s";
    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(User user) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhoneNumber());

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

            String queryFormat = String.format(UPDATE_USERS_BY_ID, query, id);

            int row = statement.executeUpdate(queryFormat);
            return row > 0;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_BY_ID)) {

            int resultSet = 0;
            if (findById(id).isPresent()) {
                statement.setObject(1, id);
                resultSet = statement.executeUpdate();
            }
            return resultSet > 0;

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserDto> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            UserDto user = new UserDto();
            while (resultSet.next()) {
                user = UserMapper.getInstance().resultSetToDto(resultSet);
            }

            if (user.getUsername() == null) {
                return Optional.empty();
            } else return Optional.of(user);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserDto> findAll() throws DaoException {
        List<UserDto> userList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userList.add(UserMapper.getInstance().resultSetToDto(resultSet));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public Optional<UserDto> findByUsername(String username) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            UserDto user = new UserDto();
            while (resultSet.next()) {
                user = UserMapper.getInstance().resultSetToDto(resultSet);
            }

            if (user.getUsername() == null) {
                return Optional.empty();
            } else return Optional.of(user);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Long> findUserRoleByUsername(String username) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_ID)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            Long role = null;
            while (resultSet.next()) {
                role = resultSet.getLong(USER_ROLE_ID_IN_DB);
            }

            return Optional.ofNullable(role);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            UserDto user = new UserDto();
            while (resultSet.next()) {
                user = UserMapper.getInstance().resultSetToDto(resultSet);
            }

            if (user.getUsername() == null) {
                return Optional.empty();
            } else return Optional.of(user);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserDto> findUserByPhoneNumber(String contact) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PHONE_NUMBER)) {

            statement.setString(1, contact);
            ResultSet resultSet = statement.executeQuery();

            UserDto user = new UserDto();
            while (resultSet.next()) {
                user = UserMapper.getInstance().resultSetToDto(resultSet);
            }

            if (user.getUsername() == null) {
                return Optional.empty();
            } else return Optional.of(user);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateUserStatusByUserId(Long userId, Long role) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_STATUS_BY_ID)) {

            statement.setLong(1, role);
            statement.setLong(2, userId);

            return statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<UserDto> findAllByUserFields(String query) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            String searchQuery = String.format(SEARCHING_USER, query);

            ResultSet resultSet = statement.executeQuery(searchQuery);

            List<UserDto> userDtoList = new ArrayList<>();

            while (resultSet.next()) {
                userDtoList.add(UserMapper.getInstance().resultSetToDto(resultSet));
            }

            return userDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

}
