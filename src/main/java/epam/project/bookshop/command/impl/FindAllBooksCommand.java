package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;

public class FindAllBooksCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        BookServiceImpl bookService = BookServiceImpl.getInstance();

        try {
            List<BookDto> bookList = bookService.findAll();

            request.setAttribute(BOOK_LIST, bookList);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return BOOK_PAGE;
    }
}
