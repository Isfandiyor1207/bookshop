package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class FindBookInformationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String bookId = request.getParameter(BOOK_ID);

        BookService bookService= BookServiceImpl.getInstance();

        try {
            Optional<BookDto> optionalBookDto = bookService.findById(Long.valueOf(bookId));

            if (optionalBookDto.isPresent()){
                request.setAttribute(BOOK_INFORMATION, optionalBookDto.get());
            } else {
                request.setAttribute(WARN_BOOK_IS_AVAILABLE_BY_NAME, ERROR_BOOK_IS_NOT_AVAILABLE);
            }

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return WebPageName.BOOK_ONE_INFO_PAGE;
    }
}
