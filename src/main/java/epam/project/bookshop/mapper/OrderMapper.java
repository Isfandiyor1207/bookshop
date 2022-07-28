package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class OrderMapper implements BaseMapper<OrderDto> {

    private static final Logger logger = LogManager.getLogger();
    private static final OrderMapper instance = new OrderMapper();

    private OrderMapper() {
    }

    public static OrderMapper getInstance() {
        return instance;
    }

    @Override
    public OrderDto resultSetToDto(ResultSet resultSet) throws DaoException {

        OrderDto orderDto = new OrderDto();

        try {
            orderDto.setId(resultSet.getLong(ID));
            orderDto.setOrderPrice(resultSet.getDouble(ORDER_PRICE));
            orderDto.setDelivered(resultSet.getBoolean(BOOK_DELIVERED));
            orderDto.setOrderQuantity(resultSet.getLong(BOOK_ORDER_QUANTITY));
            orderDto.setUserId(resultSet.getLong(USER_ID));
            orderDto.setBookId(resultSet.getLong(BOOK_ID));
            return orderDto;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

}
