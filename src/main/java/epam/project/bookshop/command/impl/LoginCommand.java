package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String username =  request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        boolean authenticate = userService.authenticate(username, password);

        String page;

        if (authenticate){
            request.setAttribute("user", username);
            page = "pages/book.jsp";
        }else {
            request.setAttribute("login_error", "Login or Password is incorrect!");
            page = "index.jsp";
        }
        return page;
    }
}
