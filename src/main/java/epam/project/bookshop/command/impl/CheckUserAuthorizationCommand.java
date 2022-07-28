package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckUserAuthorizationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String attribute = (String) request.getSession().getAttribute(ParameterName.USERNAME);

        if (attribute == null){
            return WebPageName.LOGIN_PAGE;
        } else {
            OrderBookCommand orderBookCommand=new OrderBookCommand();
            return orderBookCommand.execute(request);
        }

    }
}
