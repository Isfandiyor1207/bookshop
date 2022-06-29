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

import static epam.project.bookshop.command.ParameterName.DELETE_ID;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class DeleteAuthorCommand implements Command{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(DELETE_ID);
        AuthorService authorService = AuthorServiceImpl.getInstance();

        logger.info("Author id: " + id);

        try {
            if (!authorService.deleteById(Long.valueOf(id))) {
                request.setAttribute(WORN_DELETED, ERROR_AUTHOR_NOT_DELETED_MSG);
            }
            return WebPageName.AUTHOR_PAGE;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
