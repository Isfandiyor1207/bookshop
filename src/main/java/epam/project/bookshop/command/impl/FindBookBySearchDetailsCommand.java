package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;

public class FindBookBySearchDetailsCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String bookName = request.getParameter(BOOK_NAME);
        String authorName = request.getParameter(AUTHOR_FIO);
        String genreId = request.getParameter(GENRE_ID);

        Map<String, String> searchingMap = new HashMap<>();

        searchingMap.put(BOOK_NAME, bookName);
        searchingMap.put(AUTHOR_FIO, authorName);
        searchingMap.put(GENRE_ID, genreId);

        BookServiceImpl bookService=BookServiceImpl.getInstance();

        try {
            List<BookDto> bookDtoList = bookService.findBySearchingDetail(searchingMap);
            request.setAttribute(ParameterName.BOOK_LIST, bookDtoList);
            return BOOK_PAGE;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
