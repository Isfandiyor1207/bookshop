package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        UserService userService = UserServiceImpl.getInstance();
        boolean login;

        String page;
        HttpSession session = request.getSession();

        Map<String, String> userLogin = new HashMap<>();

        userLogin.put(USERNAME, username);
        userLogin.put(PASSWORD, password);

        try {
            login = userService.authenticate(userLogin);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        if (login) {
            Long roleId = 1L;
            Optional<UserDto> optionalUserDto = Optional.empty();
            try {
                optionalUserDto = userService.findUserByUsername(username);
                roleId = optionalUserDto.get().getRoleId();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            request.setAttribute(USER, username);
            session.setAttribute(USERNAME, username);
            session.setAttribute(USER_ROLE_ID, roleId);
            session.setAttribute(USER_ID, optionalUserDto.get().getId());

            if (roleId == 0) {
                page = ADMIN_PAGE;
            } else {
                page = INDEX_PAGE;
            }

        } else {
            for (Map.Entry<String, String> entry : userLogin.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            page = LOGIN_PAGE;
        }
        return page;
    }

}
