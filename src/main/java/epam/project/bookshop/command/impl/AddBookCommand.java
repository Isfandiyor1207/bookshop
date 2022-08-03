package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.dao.impl.BookDaoImpl;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.AttachmentServiceImpl;
import epam.project.bookshop.service.impl.BookServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.BOOK_CREATE_PAGE;
import static epam.project.bookshop.command.WebPageName.BOOK_PAGE;

public class AddBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put(BOOK_NAME, request.getParameter(BOOK_NAME));
        bookMap.put(BOOK_ISBN, request.getParameter(BOOK_ISBN));
        bookMap.put(BOOK_PUBLISHER_NAME, request.getParameter(BOOK_PUBLISHER_NAME));
        bookMap.put(BOOK_PUBLISHING_YEAR, request.getParameter(BOOK_PUBLISHING_YEAR));
        bookMap.put(BOOK_PRICE, request.getParameter(BOOK_PRICE));
        bookMap.put(BOOK_TOTAL, request.getParameter(BOOK_TOTAL));
        bookMap.put(AUTHOR_ID, Arrays.toString(request.getParameterValues(AUTHOR_ID)));
        bookMap.put(GENRE_ID, Arrays.toString(request.getParameterValues(GENRE_ID)));
        bookMap.put(BOOK_DESCRIPTION, request.getParameter(BOOK_DESCRIPTION));

        try {
            Part part = request.getPart(ATTACHMENT);
            bookMap.put(ATTACHMENT_CONTENT_TYPE, part.getContentType());
            bookMap.put(ATTACHMENT_NAME, part.getSubmittedFileName());
        } catch (IOException | ServletException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        BookServiceImpl bookService = BookServiceImpl.getInstance();

        try {
            if (bookService.add(bookMap)) {

                try {
                    AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
                    Long fileId = attachmentService.addFile(request.getPart(ATTACHMENT));

                    Optional<BookDto>  optionalBook = bookService.findByName(bookMap.get(BOOK_NAME));

                    attachmentService.attachFileToBook(optionalBook.get().getId(), fileId, false);

                } catch (ServiceException | IOException | ServletException e) {
                    logger.error("Image is not added");
                    throw new CommandException(e);
                }
                return BOOK_PAGE;
            } else {

                for (Map.Entry<String, String> entry : bookMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                return BOOK_CREATE_PAGE;
            }
        } catch (ServiceException e) {
            logger.error("Book is not added:");
            throw new CommandException(e);
        }
    }
}
