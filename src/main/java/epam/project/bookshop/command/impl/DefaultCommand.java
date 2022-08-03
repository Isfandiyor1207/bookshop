package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;

import static epam.project.bookshop.validation.ValidationParameterName.*;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(WARN_DEFAULT, ERROR_MSG_DEFAULT);
        return WebPageName.DEFAULT_PAGE;
    }
}
