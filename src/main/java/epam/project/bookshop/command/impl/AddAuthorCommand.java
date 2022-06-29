package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.service.GenreService;
import epam.project.bookshop.service.impl.AuthorServiceImpl;
import epam.project.bookshop.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;

public class AddAuthorCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        AuthorService authorService = AuthorServiceImpl.getInstance();

        Map<String, String> authorMap = new HashMap<>();
        authorMap.put(AUTHOR_FIO, request.getParameter(AUTHOR_FIO));
        logger.info("author full name: " + request.getParameter(AUTHOR_FIO));
        String page;
        try {
            if (authorService.add(authorMap)) {
                page = WebPageName.AUTHOR_PAGE;
            } else {

                for (Map.Entry<String, String> entry : authorMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                page = WebPageName.AUTHOR_CREATE_PAGE;
            }

        } catch (ServiceException e) {
            logger.error("Author is not added.");
            throw new CommandException(e);
        }
        return page;
    }
}
