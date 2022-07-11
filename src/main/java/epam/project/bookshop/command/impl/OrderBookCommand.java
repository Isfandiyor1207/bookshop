//package epam.project.bookshop.command.impl;
//
//import epam.project.bookshop.command.Command;
//import epam.project.bookshop.exception.CommandException;
//import epam.project.bookshop.exception.ServiceException;
//import epam.project.bookshop.service.BookService;
//import epam.project.bookshop.service.impl.BookServiceImpl;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import static epam.project.bookshop.command.ParameterName.BOOK_ID;
//
//public class OrderBookCommand implements Command {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    @Override
//    public String execute(HttpServletRequest request) throws CommandException {
//
//        String bookId = request.getParameter(BOOK_ID);
//
//        BookService bookService=BookServiceImpl.getInstance();
//
//        try {
//            return bookService.findById(Long.valueOf(bookId));
//        } catch (ServiceException e) {
//            logger.error(e);
//            throw new CommandException(e);
//        }
//    }
//}
