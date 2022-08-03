package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static epam.project.bookshop.command.WebPageName.*;

public class FindAllUsersCommand implements Command {
    private static final Logger logger= LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();

        try {
            List<UserDto> userList = userService.findAll();
            request.setAttribute(ParameterName.USER_LIST, userList);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return USERS_PAGE;
    }
}
