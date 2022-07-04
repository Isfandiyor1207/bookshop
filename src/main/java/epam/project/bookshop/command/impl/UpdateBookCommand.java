package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.ParameterName.PHONE_NUMBER;
import static epam.project.bookshop.command.WebPageName.*;

public class UpdateBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        Map<String, String> userMap = new HashMap<>();
        userMap.put(ID, String.valueOf(request.getSession().getAttribute("book_id")));
        userMap.put(BOOK_NAME, request.getParameter(BOOK_NAME));
        userMap.put(BOOK_ISBN, request.getParameter(BOOK_ISBN));
        userMap.put(BOOK_PUBLISHER_NAME, request.getParameter(BOOK_PUBLISHER_NAME));
        userMap.put(BOOK_PUBLISHING_YEAR, request.getParameter(BOOK_PUBLISHING_YEAR));
        userMap.put(BOOK_PRICE, request.getParameter(BOOK_PRICE));
        userMap.put(BOOK_TOTAL, request.getParameter(BOOK_TOTAL));
        userMap.put(GENRE_ID, request.getParameter(GENRE_ID));
        userMap.put(AUTHOR_ID, request.getParameter(AUTHOR_ID));

        logger.info("Book info: " +  userMap.toString());

        BookService bookService = BookServiceImpl.getInstance();

        try {
            boolean update = bookService.update(userMap);

            if (update) {
                return BOOK_PAGE;
            } else {
                for (Map.Entry<String, String> entry : userMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                return BOOK_UPDATE_PAGE;
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
