package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.OrderService;
import epam.project.bookshop.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;

public class FindOrdersBySearchingDetailsCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String bookName = request.getParameter(BOOK_NAME);
        String username = request.getParameter(USERNAME);

        Map<String, String> orderMap = new HashMap<>();

        orderMap.put(BOOK_NAME, bookName);
        orderMap.put(USERNAME, username);

        OrderService orderService = OrderServiceImpl.getInstance();

        try {
            List<OrderDto> orderByUser = orderService.findAllOrdersBySearchingField(orderMap);

            request.setAttribute(ALL_ORDERS, orderByUser);

            return WebPageName.ALL_ORDERS;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
