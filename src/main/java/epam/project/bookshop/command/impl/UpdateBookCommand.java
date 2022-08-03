package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
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
import static epam.project.bookshop.command.WebPageName.BOOK_PAGE;
import static epam.project.bookshop.command.WebPageName.BOOK_UPDATE_PAGE;

public class UpdateBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {

            String bookId = String.valueOf(request.getSession().getAttribute(BOOK_ID));

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put(ID, bookId);
            bookMap.put(BOOK_NAME, request.getParameter(BOOK_NAME));
            bookMap.put(BOOK_ISBN, request.getParameter(BOOK_ISBN));
            bookMap.put(BOOK_PUBLISHER_NAME, request.getParameter(BOOK_PUBLISHER_NAME));
            bookMap.put(BOOK_PUBLISHING_YEAR, request.getParameter(BOOK_PUBLISHING_YEAR));
            bookMap.put(BOOK_PRICE, request.getParameter(BOOK_PRICE));
            bookMap.put(BOOK_TOTAL, request.getParameter(BOOK_TOTAL));
            bookMap.put(AUTHOR_ID, Arrays.toString(request.getParameterValues(AUTHOR_ID)));
            bookMap.put(GENRE_ID, Arrays.toString(request.getParameterValues(GENRE_ID)));
            bookMap.put(BOOK_DESCRIPTION, request.getParameter(BOOK_DESCRIPTION));


            Part part = request.getPart(ATTACHMENT);

            if (!part.getSubmittedFileName().isEmpty()) {
                bookMap.put(ATTACHMENT_CONTENT_TYPE, part.getContentType());
                bookMap.put(ATTACHMENT_NAME, part.getSubmittedFileName());
            }


            BookServiceImpl bookService = BookServiceImpl.getInstance();

            if (bookService.update(bookMap)) {

                String fileName =  part.getSubmittedFileName();

                if (!fileName.isEmpty()) {
                    AttachmentServiceImpl attachmentService = AttachmentServiceImpl.getInstance();
                    Long fileId = attachmentService.addFile(request.getPart(ATTACHMENT));

                    attachmentService.attachFileToBook(Long.valueOf(bookId), fileId, true);
                }
                return BOOK_PAGE;
            } else {

                for (Map.Entry<String, String> entry : bookMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                return BOOK_UPDATE_PAGE;
            }
        } catch (ServiceException | IOException | ServletException e) {
            logger.error("Book is not added:");
            throw new CommandException(e);
        }
    }
}
