package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SignUpCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String repeatedPassword = request.getParameter("psw-repeat");
        String password = request.getParameter("password");

        UserServiceImpl instance = UserServiceImpl.getInstance();

        String page;
        if (repeatedPassword.equals(password)){
            User user=User.builder()
                    .firstName(firstname)
                    .lastName(lastname)
                    .username(username)
                    .email(email)
                    .password(Base64.getEncoder().encodeToString(password.getBytes()))
                    .phoneNumber(phoneNumber)
                    .build();

            instance.addUser(user);
            page="index.jsp";
        }else {
            request.setAttribute("psw_error", "Password is not the same.");
            page = "pages/signUp.jsp";
        }


        return page;
    }
}
