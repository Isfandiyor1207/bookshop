package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;

public class AddUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String firstname = request.getParameter(ParameterName.FIRSTNAME);
        String lastname = request.getParameter(ParameterName.LASTNAME);
        String username = request.getParameter(ParameterName.USERNAME);
        String email = request.getParameter(ParameterName.EMAIL);
        String phoneNumber = request.getParameter(ParameterName.PHONE_NUMBER);
        String repeatedPassword = request.getParameter(ParameterName.PSW_REPEAT);
        String password = request.getParameter(ParameterName.PASSWORD);

        UserServiceImpl userService = UserServiceImpl.getInstance();

        String page;
        if (repeatedPassword.equals(password)) {
            User user = User.builder()
                    .firstName(firstname)
                    .lastName(lastname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .build();

            try {
                if (userService.add(user)){
                    page = WebPageName.INDEX_PAGE; // fixme redirect is not working
                }else {
                    page = WebPageName.SIGNUP_PAGE;
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(ParameterName.PSW_REPEAT, "Password is not the same.");
            page = WebPageName.SIGNUP_PAGE;
        }
        return page;
    }
}
