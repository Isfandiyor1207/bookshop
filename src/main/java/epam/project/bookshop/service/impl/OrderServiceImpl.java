package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.impl.OrderDaoImpl;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.Order;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.OrderService;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.validation.OrderValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();
    private static final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    private static final OrderServiceImpl instance = new OrderServiceImpl();
    private static final BookService bookService = BookServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final OrderValidation orderValidation = OrderValidation.getInstance();


    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return orderDao.deleteById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> findAll() throws ServiceException {
        try {
            List<OrderDto> orderDtoList = orderDao.findAll();

            List<OrderDto> dtoList = new ArrayList<>();

            for (OrderDto orderDto : orderDtoList) {
                orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());
                orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
                dtoList.add(orderDto);
            }
            return dtoList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<OrderDto> findById(Long id) throws ServiceException {
        try {
            Optional<OrderDto> optionalOrderDto = orderDao.findById(id);
            OrderDto orderDto = optionalOrderDto.get();

            orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
            orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());

            return Optional.of(orderDto);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> orderedBookMap) throws ServiceException {

        boolean orderedValidation = orderValidation.validateOrderedBookInformation(orderedBookMap);

        if (!orderedValidation) {
            logger.info("Book order is not valid.");
            return false;
        }

        boolean orderedInfo = true;

        Optional<UserDto> optionalUserDto = userService.findById(Long.valueOf(orderedBookMap.get(USER_ID)));
        if (!optionalUserDto.isPresent()) {
            logger.info("User is not available to order");
            orderedBookMap.put(WORN_USER, ERROR_USER_NOT_EXIST_MSG);
            orderedInfo = false;
        }

        Optional<BookDto> optionalBookDto = bookService.findById(Long.valueOf(orderedBookMap.get(BOOK_ID)));

        if (!optionalBookDto.isPresent()) {
            logger.info("Book is not available to order");
            orderedBookMap.put(WARN_BOOK_IS_NOT_AVAILABLE, ERROR_BOOK_IS_NOT_AVAILABLE);
            orderedInfo = false;
        }

        if (!orderedInfo) {
            return false;
        } else {
            Order order = new Order();
            order.setUserId(Long.valueOf(orderedBookMap.get(USER_ID)));
            order.setBookId(Long.valueOf(orderedBookMap.get(BOOK_ID)));
            Double price = optionalBookDto.get().getPrice() * Double.parseDouble(orderedBookMap.get(BOOK_ORDER_QUANTITY));
            order.setOrderPrice(price);
            order.setOrderQuantity(Long.valueOf(orderedBookMap.get(BOOK_ORDER_QUANTITY)));

            Long save;
            try {
                save = orderDao.save(order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            return save > 0;
        }
    }

    @Override
    public boolean update(Map<String, String> update) throws ServiceException {
        return false;
    }

    @Override
    public List<OrderDto> findOrderByUserId(Long userId) throws ServiceException {
        try {
            List<OrderDto> orderDtoList = orderDao.findAllOrderByUserId(userId);

            List<OrderDto> dtoList = new ArrayList<>();

            for (OrderDto orderDto : orderDtoList) {
                orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());
                orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
                dtoList.add(orderDto);
            }
            return dtoList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> findOrderByBookId(Long bookId) throws ServiceException {
        try {
            List<OrderDto> orderDtoList = orderDao.findAllOrderByBookId(bookId);

            List<OrderDto> dtoList = new ArrayList<>();

            for (OrderDto orderDto : orderDtoList) {
                orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());
                orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
                dtoList.add(orderDto);
            }
            return dtoList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> findAllDeliveredOrder() throws ServiceException {

        try {
            List<OrderDto> allDeliveredOrder = orderDao.findAllDeliveredOrder();

            List<OrderDto> orderDtoList = new ArrayList<>();

            for (OrderDto orderDto : allDeliveredOrder) {
                orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
                orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());
                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> findAllNotDeliveredOrder() throws ServiceException {
        try {
            List<OrderDto> allDeliveredOrder = orderDao.findAllNotDeliveredOrder();

            List<OrderDto> orderDtoList = new ArrayList<>();

            for (OrderDto orderDto : allDeliveredOrder) {
                orderDto.setBookDto(bookService.findById(orderDto.getBookId()).get());
                orderDto.setUserDto(userService.findById(orderDto.getUserId()).get());
                orderDtoList.add(orderDto);
            }

            return orderDtoList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeOrderDeliveredStatus(boolean isDelivered, Long orderId) throws ServiceException {
        try {
            orderDao.changeOrderDeliveredStatus(isDelivered, orderId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<OrderDto> findAllOrdersBySearchingField(Map<String, String> orderMap) throws ServiceException {
        String bookName = orderMap.get(BOOK_NAME);
        String username = orderMap.get(USERNAME);

        List<OrderDto> orderDto = new ArrayList<>();

        if (!bookName.isEmpty() && !username.isEmpty()) {
            Optional<UserDto> userByUsername = userService.findUserByUsername(username);
            List<BookDto> bookDtoList = bookService.findAllBookByBookName(bookName);
            List<OrderDto> orderDtoList = findOrderByUserId(userByUsername.get().getId());

            for (BookDto bookDto : bookDtoList) {
                for (OrderDto dto : orderDtoList) {
                    if (dto.getBookId().equals(bookDto.getId())) {
                        OrderDto order = new OrderDto();
                        order.setUserDto(userByUsername.get());
                        order.setBookDto(bookDto);
                        order.setOrderPrice((double) (bookDto.getPrice() * dto.getOrderQuantity()));
                        order.setOrderQuantity(dto.getOrderQuantity());
                        orderDto.add(order);
                    }
                }
            }
        }

        if (bookName.isEmpty() && !username.isEmpty()) {
            Optional<UserDto> optionalUserDto = userService.findUserByUsername(username);
            orderDto = findOrderByUserId(optionalUserDto.get().getId());
        }

        if (!bookName.isEmpty() && username.isEmpty()) {
            List<BookDto> bookDtoList = bookService.findAllBookByBookName(bookName);

            for (BookDto bookDto : bookDtoList) {
                orderDto.addAll(findOrderByBookId(bookDto.getId()));
            }
        }

        return orderDto;
    }

    @Override
    public void changeBookQuantityByOrderId(Long orderId) throws ServiceException {
        Optional<OrderDto> optionalOrderDto = findById(orderId);
        bookService.changeQuantityByBookId(optionalOrderDto.get().getBookId(), optionalOrderDto.get().getOrderQuantity());
    }
}
