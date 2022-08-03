package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static epam.project.bookshop.command.ParameterName.AUTH_USER;
import static epam.project.bookshop.command.ParameterName.VERIFICATION_CODE;
import static epam.project.bookshop.command.WebPageName.UPDATE_PASSWORD_PAGE;
import static epam.project.bookshop.command.WebPageName.VERIFICATION_PAGE;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_VERIFICATION_CODE;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_VERIFICATION_PASSWORD_ERROR;

public class PasswordVerificationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String verificationCode = request.getParameter(VERIFICATION_CODE);
        UserDto authUser = (UserDto) request.getSession().getAttribute(AUTH_USER);

        if (verificationCode.equals(authUser.getCode())) {
            logger.info("User verification code is correct!");
            return UPDATE_PASSWORD_PAGE;
        } else {
            request.setAttribute(WARN_VERIFICATION_PASSWORD_ERROR, ERROR_VERIFICATION_CODE);
            return VERIFICATION_PAGE;
        }
    }
}
