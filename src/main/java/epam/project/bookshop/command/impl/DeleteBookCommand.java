package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.command.ParameterName.DELETE_ID;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_BOOK_IS_NOT_DELETED;
import static epam.project.bookshop.validation.ValidationParameterName.WORN_DELETED;

public class DeleteBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(DELETE_ID);
        BookService bookService = BookServiceImpl.getInstance();

        logger.info("book id: " + id);

        try {
            if (!bookService.deleteById(Long.valueOf(id))) {
                request.setAttribute(WORN_DELETED, ERROR_BOOK_IS_NOT_DELETED);
            }
            return WebPageName.BOOK_PAGE;

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

    }
}
