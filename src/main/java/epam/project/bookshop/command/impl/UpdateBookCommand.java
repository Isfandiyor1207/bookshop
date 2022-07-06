package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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

        Map<String, String> bookMap = new HashMap<>();
        bookMap.put(ID, String.valueOf(request.getSession().getAttribute("book_id")));
        bookMap.put(BOOK_NAME, request.getParameter(BOOK_NAME));
        bookMap.put(BOOK_ISBN, request.getParameter(BOOK_ISBN));
        bookMap.put(BOOK_PUBLISHER_NAME, request.getParameter(BOOK_PUBLISHER_NAME));
        bookMap.put(BOOK_PUBLISHING_YEAR, request.getParameter(BOOK_PUBLISHING_YEAR));
        bookMap.put(BOOK_PRICE, request.getParameter(BOOK_PRICE));
        bookMap.put(BOOK_TOTAL, request.getParameter(BOOK_TOTAL));
        bookMap.put(GENRE_ID, request.getParameter(GENRE_ID));
        bookMap.put(AUTHOR_ID, request.getParameter(AUTHOR_ID));

        try {
            Part part = request.getPart(ATTACHMENT);
            bookMap.put(ATTACHMENT_CONTENT_TYPE, part.getContentType());
            bookMap.put(ATTACHMENT_NAME, part.getSubmittedFileName());
        } catch (IOException | ServletException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        logger.info("Book info: " +  bookMap);

        BookService bookService = BookServiceImpl.getInstance();

        try {
            boolean update = bookService.update(bookMap);

            if (update) {
                return BOOK_PAGE;
            } else {
                for (Map.Entry<String, String> entry : bookMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                return BOOK_UPDATE_PAGE;
            }

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
