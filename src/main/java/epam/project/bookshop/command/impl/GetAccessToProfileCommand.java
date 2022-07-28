package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static epam.project.bookshop.command.ParameterName.USERNAME;
import static epam.project.bookshop.command.WebPageName.LOGIN_PAGE;
import static epam.project.bookshop.command.WebPageName.USER_PROFILE;

public class GetAccessToProfileCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String attribute = (String) request.getSession().getAttribute(USERNAME);

        if (attribute == null) {
            return LOGIN_PAGE;
        } else return USER_PROFILE;
    }
}
