package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeDeliveredStatusCommand implements Command {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String orderId = request.getParameter("order_id");
        String isDelivered = request.getParameter("order_delivered");

        logger.info("Order id: " + orderId);
        logger.info("Order delivered: " + isDelivered);

        OrderServiceImpl orderService=OrderServiceImpl.getInstance();

        try {
            orderService.changeOrderDeliveredStatus(Boolean.parseBoolean(isDelivered), Long.valueOf(orderId));
            FindAllOrdersCommand command=new FindAllOrdersCommand();
            return command.execute(request);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

    }
}
