package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.UserDao;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.pool.ConnectionPool;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDaoImpl implements UserDao {

    static final String SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ? AND deleted = false";
    public static final String SELECT_BY_ID = "SELECT * FROM users WHERE  id = ? AND deleted = false";
    public static final String SELECT_ALL = "SELECT * FROM   users WHERE deleted=false";
    public final String DELETE_USERS_BY_ID = "UPDATE users SET deleted = true WHERE id =? AND deleted = false";
    public final String INSERT_USER = "INSERT INTO users(id, first_name, last_name, username, password, email, phone_number, deleted, created_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static UserDaoImpl instance;
    static ReentrantLock lock = new ReentrantLock();
    static AtomicBoolean isCreated = new AtomicBoolean();

    public static UserDaoImpl getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new UserDaoImpl();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean save(User user) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setObject(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getEmail());
            statement.setString(7, user.getEmail());
            statement.setBoolean(8, false);
            statement.setTime(9, Time.valueOf(LocalTime.now()));

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updated(User user) {

        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_BY_ID)) {

            if (findById(id).isPresent()) {
                statement.setObject(1, id);
                statement.executeQuery();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setUsername(resultSet.getString("username"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
            }

            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            User user = new User();

            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setUsername(resultSet.getString("username"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public Optional<User> findByUsername(String username) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_USERNAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setUsername(resultSet.getString("username"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
            }

            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
