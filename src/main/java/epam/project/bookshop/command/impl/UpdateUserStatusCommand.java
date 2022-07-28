package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.entity.type.Role;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.UserServiceImpl;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.validation.ValidationParameterName.*;

public class UpdateUserStatusCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String role = request.getParameter("user_role");
        String userId= String.valueOf(request.getSession().getAttribute(ParameterName.USER_ID));

        long roleId = 0;

        Role[] values = Role.values();
        for (Role value : values) {
            if (value.toString().equalsIgnoreCase(role)){
                roleId = value.ordinal();
            }
        }

        UserServiceImpl userService=UserServiceImpl.getInstance();

        try {
            if (!userService.updateUserStatusByUserId(Long.valueOf(userId), roleId)){
                return WebPageName.USERS_PAGE;
            } else {
                request.setAttribute(WARN_USER_UPDATE_STATUS, ERROR_USER_UPDATE_STATUS);
                return WebPageName.USER_UPDATE_STATUS;
            }
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
