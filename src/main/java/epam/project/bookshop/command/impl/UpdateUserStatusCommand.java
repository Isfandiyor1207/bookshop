package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.entity.type.Role;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.command.ParameterName.USER_ID;
import static epam.project.bookshop.command.ParameterName.USER_ROLE;
import static epam.project.bookshop.command.WebPageName.USERS_PAGE;
import static epam.project.bookshop.command.WebPageName.USER_UPDATE_STATUS;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_USER_UPDATE_STATUS;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_USER_UPDATE_STATUS;

public class UpdateUserStatusCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String role = request.getParameter(USER_ROLE);
        String userId = String.valueOf(request.getSession().getAttribute(USER_ID));

        long roleId = 0;

        Role[] values = Role.values();
        for (Role value : values) {
            if (value.toString().equalsIgnoreCase(role)) {
                roleId = value.ordinal();
            }
        }

        UserServiceImpl userService = UserServiceImpl.getInstance();

        try {
            if (!userService.updateUserStatusByUserId(Long.valueOf(userId), roleId)) {
                return USERS_PAGE;
            } else {
                request.setAttribute(WARN_USER_UPDATE_STATUS, ERROR_USER_UPDATE_STATUS);
                return USER_UPDATE_STATUS;
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
