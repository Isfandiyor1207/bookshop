package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.OrderDao;
import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.entity.Order;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.mapper.OrderMapper;
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

public class OrderDaoImpl implements OrderDao {

    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final String INSERT_ORDER = "INSERT INTO order_book(user_id, book_id, quantity, order_price) values (?, ?, ?, ?) RETURNING id;";
    private static final String SELECT_ORDER_BY_USER_ID = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE user_id = ? and deleted = false;";
    private static final String SELECT_ORDER_BY_BOOK_ID = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE book_id = ? and deleted = false;";
    private static final String SELECT_ALL_ORDER = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE deleted = false;";
    private static final String SELECT_ALL_DELIVERED_ORDERS = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE deleted = false and delivered=true;";
    private static final String SELECT_ALL_NOT_DELIVERED_ORDERS = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE deleted = false and delivered=false;";
    private static final String SELECT_BY_ID = "SELECT id, user_id, book_id, quantity, order_price, delivered FROM order_book WHERE deleted = false and id = ?";
    private static final String DELETE_BY_ID = "UPDATE order_book SET deleted = true WHERE id = ? and deleted=false;";
    private static final String UPDATE_DELIVERED_STATUS_BY_ID = "UPDATE order_book SET delivered = ? WHERE id = ? and deleted=false;";

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Long save(Order order) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {

            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getBookId());
            statement.setLong(3, order.getOrderQuantity());
            statement.setDouble(4, order.getOrderPrice());

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
    public boolean updated(String query, Long id) throws DaoException {

        // todo do the update method();

        return false;
    }

    @Override
    public boolean deleteById(Long id) throws DaoException, ServiceException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {

            statement.setLong(1, id);

            return statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<OrderDto> findById(Long id) throws DaoException {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            OrderDto orderDto = new OrderDto();

            while (resultSet.next()) {
                orderDto = OrderMapper.getInstance().resultSetToDto(resultSet);
            }

            if (orderDto.getId() != null) {
                return Optional.of(orderDto);
            }
            return Optional.empty();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDto> findAll() throws DaoException {

        List<OrderDto> orderDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDER)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDto orderDto = OrderMapper.getInstance().resultSetToDto(resultSet);
                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDto> findAllOrderByUserId(Long userId) throws DaoException {

        List<OrderDto> orderDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_USER_ID)) {

            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderDtoList.add(OrderMapper.getInstance().resultSetToDto(resultSet));
            }

            return orderDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDto> findAllOrderByBookId(Long bookId) throws DaoException {
        List<OrderDto> orderDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_BOOK_ID)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderDtoList.add(OrderMapper.getInstance().resultSetToDto(resultSet));
            }

            return orderDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDto> findAllDeliveredOrder() throws DaoException {

        List<OrderDto> orderDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DELIVERED_ORDERS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDto orderDto = OrderMapper.getInstance().resultSetToDto(resultSet);
                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDto> findAllNotDeliveredOrder() throws DaoException {

        List<OrderDto> orderDtoList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_NOT_DELIVERED_ORDERS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDto orderDto = OrderMapper.getInstance().resultSetToDto(resultSet);
                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void changeOrderDeliveredStatus(boolean isDelivered, Long orderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DELIVERED_STATUS_BY_ID)) {

            statement.setBoolean(1, isDelivered);
            statement.setLong(2, orderId);

            statement.execute();

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
