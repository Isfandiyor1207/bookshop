package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter("id");
        UserService userService= UserServiceImpl.getInstance();

        if (userService.deleteById(Long.valueOf(id))){
            return "index.jsp";
        }else {
            request.setAttribute("deleted_error", "User has not deleted!");
            String uri = request.getRequestURI();
            return uri.substring(uri.lastIndexOf("/") + 1);
        }
    }
}
