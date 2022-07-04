package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class FindBookByIdCommand implements Command {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String id= request.getParameter("book_id");

        logger.info("book id: " + id);
        BookService bookService= BookServiceImpl.getInstance();

        try {
            Optional<Book> optionalBook = bookService.findById(Long.valueOf(id));

            if (optionalBook.isPresent()){
                HttpSession session = request.getSession();
                session.setAttribute("book_id", id);
            } else request.setAttribute("book_update_error", "book is not find by this id!");

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return WebPageName.BOOK_UPDATE_PAGE;
    }
}
