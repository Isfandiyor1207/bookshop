package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.ParameterName.AUTHOR_FIO;
import static epam.project.bookshop.command.ParameterName.ID;

public class UpdateAuthorCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        AuthorService authorService = AuthorServiceImpl.getInstance();

        String authorId = (String) request.getSession().getAttribute(AUTHOR_ID);

        Map<String, String> authorMap = new HashMap<>();
        authorMap.put(AUTHOR_FIO, request.getParameter(AUTHOR_FIO));
        authorMap.put(ID, authorId);
        String page;
        try {
            if (authorService.update(authorMap)) {
                page = WebPageName.AUTHOR_PAGE;
            } else {

                for (Map.Entry<String, String> entry : authorMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                page = WebPageName.AUTHOR_UPDATE_PAGE;
            }

        } catch (ServiceException e) {
            logger.error("Genre is not updated. Error thrown from AddGenreCommand.");
            throw new CommandException(e);
        }
        return page;
    }
}
