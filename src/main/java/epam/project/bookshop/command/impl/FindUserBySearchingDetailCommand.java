package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;

public class FindUserBySearchingDetailCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Map<String, String> userMap = new HashMap<>();
        userMap.put(FIRSTNAME, request.getParameter(FIRSTNAME));
        userMap.put(LASTNAME, request.getParameter(LASTNAME));
        userMap.put(USERNAME, request.getParameter(USERNAME));
        userMap.put(EMAIL, request.getParameter(EMAIL));
        userMap.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userMap.put(USER_ROLE_ID_IN_DB, request.getParameter(ROLE_ID));

        UserService userService = UserServiceImpl.getInstance();

        try {
            List<UserDto> userDtoList = userService.findAllByUserFields(userMap);
            request.setAttribute(USER_LIST, userDtoList);

            return USERS_PAGE;
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }
    }
}
