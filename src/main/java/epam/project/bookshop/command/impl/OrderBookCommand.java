package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;

public class OrderBookCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String username =  String.valueOf(request.getSession().getAttribute(USERNAME));
        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));
        String bookId = request.getParameter(BOOK_ID);
        String quantity = request.getParameter(BOOK_ORDER_QUANTITY);

        Map<String, String> orderedMap=new HashMap<>();
        orderedMap.put(USER_ID, userId);
        orderedMap.put(BOOK_ID, bookId);
        orderedMap.put(BOOK_ORDER_QUANTITY, quantity);

        OrderServiceImpl orderService=OrderServiceImpl.getInstance();

        try {

            boolean isAdded = orderService.add(orderedMap);

            BookService bookService= BookServiceImpl.getInstance();
            Optional<BookDto> optionalBookDto = bookService.findById(Long.valueOf(bookId));

            request.setAttribute(BOOK_INFORMATION, optionalBookDto.get());

            if (!isAdded) {

                for (Map.Entry<String, String> entry : orderedMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            return WebPageName.BOOK_ONE_INFO_PAGE;

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
