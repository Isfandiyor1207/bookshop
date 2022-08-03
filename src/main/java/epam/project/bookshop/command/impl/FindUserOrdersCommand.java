package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.OrderDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.OrderServiceImpl;
import epam.project.bookshop.service.impl.UserServiceImpl;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.el.ELClass;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static epam.project.bookshop.command.ParameterName.ORDERS_BY_USER_ID;
import static epam.project.bookshop.command.ParameterName.USER_ID;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class FindUserOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();

        try {
            List<OrderDto> orderByUser = orderService.findOrderByUserId(Long.valueOf(userId));

            if (orderByUser.size()==0){
                request.setAttribute(WARN_USER_IS_NOT_ORDERED, ERROR_USER_IS_NOT_ORDERED);
            } else {
                request.setAttribute(ORDERS_BY_USER_ID, orderByUser);
            }

            return WebPageName.USER_ORDER_PAGE;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
