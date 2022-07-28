package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;

public class FindUserInformationCommand implements Command {

    public static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));

        UserService userService= UserServiceImpl.getInstance();

        try {
            Optional<UserDto> userDto = userService.findById(Long.valueOf(userId));
            logger.info("UserDto: " + userDto.get());
            request.setAttribute(USER_INFO, userDto.get());
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return USER_INFORMATION_PAGE;
    }
}
