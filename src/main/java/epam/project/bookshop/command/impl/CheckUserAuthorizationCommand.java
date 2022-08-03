package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.command.WebPageName.*;

public class CheckUserAuthorizationCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String username = (String) request.getSession().getAttribute(ParameterName.USERNAME);

        if (username == null){
            return LOGIN_PAGE;
        } else {
            logger.info("User authorization username : " + username);
            OrderBookCommand orderBookCommand=new OrderBookCommand();
            return orderBookCommand.execute(request);
        }

    }
}
