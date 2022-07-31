package epam.project.bookshop.service;

import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService extends GenericService<OrderDto> {

    List<OrderDto> findOrderByUserId(Long userId) throws ServiceException;

    List<OrderDto> findOrderByBookId(Long bookId) throws ServiceException;

    List<OrderDto> findAllDeliveredOrder() throws ServiceException;

    List<OrderDto> findAllNotDeliveredOrder() throws ServiceException;

    void changeOrderDeliveredStatus(boolean isDelivered, Long orderId) throws ServiceException;

    List<OrderDto> findAllOrdersBySearchingField(Map<String, String> orderMap) throws ServiceException;

    void changeBookQuantityByOrderId(Long orderId) throws ServiceException;
}
