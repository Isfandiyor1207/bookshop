package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.USERNAME;
import static epam.project.bookshop.command.WebPageName.*;

public class GetAccessToProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String username = (String) request.getSession().getAttribute(USERNAME);

        try {
            if (username != null) {
                UserServiceImpl userService = UserServiceImpl.getInstance();
                String page;
                Optional<UserDto> userByUsername = userService.findUserByUsername(username);
                if (userByUsername.isPresent()) {
                    if (userByUsername.get().getRoleId() == 0) {
                        page = ADMIN_PAGE;
                    } else {
                        page = USER_PROFILE;
                    }
                } else {
                    page = LOGIN_PAGE;
                }

                return page;
            } else return LOGIN_PAGE;

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
