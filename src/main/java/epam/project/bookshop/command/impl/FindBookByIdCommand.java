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
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class FindBookByIdCommand implements Command {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id= request.getParameter(BOOK_ID);
        BookService bookService= BookServiceImpl.getInstance();

        try {
            Optional<BookDto> optionalBook = bookService.findById(Long.valueOf(id));

            if (optionalBook.isPresent()){
                HttpSession session = request.getSession();
                session.setAttribute(BOOK_ID, id);
            } else request.setAttribute(WARN_BOOK_UPDATE, ERROR_BOOK_IS_NOT_AVAILABLE);

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return BOOK_UPDATE_PAGE;
    }
}
