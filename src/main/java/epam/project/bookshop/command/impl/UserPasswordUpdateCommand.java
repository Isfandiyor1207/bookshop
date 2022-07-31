package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import epam.project.bookshop.validation.ValidationParameterName;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class UserPasswordUpdateCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String password = request.getParameter(PASSWORD);
        UserDto userDto = (UserDto) request.getSession().getAttribute(AUTH_USER);

        UserService userService= UserServiceImpl.getInstance();

        Map<String, String> userMap=new HashMap<>();

        userMap.put(PASSWORD, password);
        userMap.put(ID, String.valueOf(userDto.getId()));

        try {
            boolean isUpdate = userService.update(userMap);
            String page;
            if (isUpdate){
                request.setAttribute(VERIFICATION_SUCCESS, SUCCESS_VERIFICATION);
                page = LOGIN_PAGE;
            }else {
                for (Map.Entry<String, String> entry : userMap.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                page = UPDATE_PASSWORD_PAGE;
            }
            return page;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
