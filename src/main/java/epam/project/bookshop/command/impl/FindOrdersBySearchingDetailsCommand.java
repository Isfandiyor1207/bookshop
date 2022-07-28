package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.BOOK_NAME;
import static epam.project.bookshop.command.ParameterName.USERNAME;

public class FindOrdersBySearchingDetailsCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String bookName = request.getParameter(BOOK_NAME);
        String username = request.getParameter(USERNAME);

        Map<String, String> orderMap=new HashMap<>();

        orderMap.put(BOOK_NAME, bookName);
        orderMap.put(USERNAME, username);



        return null;
    }
}
