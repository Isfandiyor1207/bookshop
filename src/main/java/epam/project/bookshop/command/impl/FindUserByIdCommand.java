package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.BookServiceImpl;
import epam.project.bookshop.service.impl.UserServiceImpl;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.USER_ID;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class FindUserByIdCommand implements Command {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id= request.getParameter(USER_ID);

        logger.info("user id: " + id);
        UserService userService= UserServiceImpl.getInstance();

        try {
            Optional<UserDto> optionalUser = userService.findById(Long.valueOf(id));

            if (optionalUser.isPresent()){
                HttpSession session = request.getSession();
                session.setAttribute(USER_ID, id);
            } else request.setAttribute(WORN_USER_UPDATE, ERROR_USER_NOT_EXIST_MSG);

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return WebPageName.USERS_UPDATE_PAGE;
    }
}
