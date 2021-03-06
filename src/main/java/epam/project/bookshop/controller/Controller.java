package epam.project.bookshop.controller;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.CommandType;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.command.impl.LogoutCommand;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static epam.project.bookshop.command.ParameterName.*;

@WebServlet(name = "helloServlet", value = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
                 maxFileSize = 1024 * 1024 * 5,
                 maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {

    private static final Logger logger= LogManager.getLogger();

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        controllerCommand(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controllerCommand(req, resp);
    }

    public void destroy() {
    }

    private void controllerCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String parameterCommand = req.getParameter(COMMAND);
        Command command = CommandType.castToCommand(parameterCommand);

        String page;
        try {

            HttpSession session = req.getSession();

            if (parameterCommand.equals(LOGOUT)) {
                session.setAttribute(CURRENT_PAGE, WebPageName.INDEX_PAGE);
                page = command.execute(req);
                resp.sendRedirect(page);
            } else {
                page = command.execute(req);
                session.setAttribute(CURRENT_PAGE, page);
                req.getRequestDispatcher(page).forward(req, resp);
            }


        } catch (CommandException e) {
            logger.error(e);
            throw new ServletException(e);
        }
    }

}