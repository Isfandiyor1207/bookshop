package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.command.ParameterName.CURRENT_LOCALE;
import static epam.project.bookshop.command.ParameterName.CURRENT_PAGE;

public class ChangeLanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String locale = request.getParameter(CURRENT_LOCALE);
        HttpSession session = request.getSession();
        if (locale != null && !locale.isEmpty()) {
            session.setAttribute(CURRENT_LOCALE, locale);
            logger.info("Locale is saved: " + locale);
        }
        return (String) session.getAttribute(CURRENT_PAGE);
    }
}
