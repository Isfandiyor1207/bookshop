package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static epam.project.bookshop.command.ParameterName.ALL_ORDERS;
import static epam.project.bookshop.command.ParameterName.USER_ID;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_USER_IS_NOT_ORDERED;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_USER_IS_NOT_ORDERED;

public class FindAllOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();

        try {
            List<OrderDto> orderByUser = orderService.findAll();

            if (orderByUser.size() == 0) {
                request.setAttribute(WARN_USER_IS_NOT_ORDERED, ERROR_USER_IS_NOT_ORDERED);
            } else {
                request.setAttribute(ALL_ORDERS, orderByUser);
            }

            return WebPageName.ALL_ORDERS;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
