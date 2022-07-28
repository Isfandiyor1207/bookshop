package epam.project.bookshop.dao;

import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.entity.Order;
import epam.project.bookshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<OrderDto, Order>{
    List<OrderDto> findAllOrderByUserId(Long userId) throws DaoException;

    List<OrderDto> findAllDeliveredOrder() throws DaoException;

    List<OrderDto> findAllNotDeliveredOrder() throws DaoException;

    void changeOrderDeliveredStatus(boolean isDelivered, Long orderId) throws DaoException;

}
