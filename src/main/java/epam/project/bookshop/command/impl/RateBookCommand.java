package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.RateService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.service.impl.RateServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.LOGIN_PAGE;

public class RateBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String username = (String) request.getSession().getAttribute(USERNAME);
        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));
        String bookId = request.getParameter(BOOK_ID);
        String rate = request.getParameter(BOOK_RATE);


        if (username != null) {
            Map<String, String> rateMap = new HashMap<>();
            rateMap.put(USER_ID, userId);
            rateMap.put(BOOK_ID, bookId);
            rateMap.put(BOOK_RATE, rate);

            RateService rateService = RateServiceImpl.getInstance();
            BookService bookService = BookServiceImpl.getInstance();

            try {
                if (rateService.add(rateMap)) {
                    Optional<BookDto> optionalBookDto = bookService.findById(Long.valueOf(bookId));
                    request.setAttribute(BOOK_INFORMATION, optionalBookDto.get());
                }

                return WebPageName.BOOK_ONE_INFO_PAGE;

            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }

        } else {
            return LOGIN_PAGE;
        }

    }

}
