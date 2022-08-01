package epam.project.bookshop.command;

import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
}