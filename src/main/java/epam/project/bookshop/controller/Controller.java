package epam.project.bookshop.controller;

import java.io.*;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.CommandType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/controller")
public class Controller extends HttpServlet {

    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String command = request.getParameter("command");// input dagi name
        Command execute = CommandType.define(command);
        String page = execute.execute(request);
        request.getRequestDispatcher(page).forward(request, response);
    }

    public void destroy() {
    }
}