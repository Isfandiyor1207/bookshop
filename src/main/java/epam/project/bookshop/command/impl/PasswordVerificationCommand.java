package epam.project.bookshop.command.impl;

import com.oracle.wls.shaded.org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM;
import epam.project.bookshop.command.Command;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.command.WebPageName.*;
import static epam.project.bookshop.validation.ValidationParameterName.SUCCESS_VERIFICATION;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_VERIFICATION_PASSWORD_ERROR;

public class PasswordVerificationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String verificationCode = request.getParameter(VERIFICATION_CODE);
        UserDto authUser = (UserDto) request.getSession().getAttribute(AUTH_USER);

        logger.info(verificationCode);
        logger.info(authUser);

        if (verificationCode.equals(authUser.getCode())) {
            return UPDATE_PASSWORD_PAGE;
        } else {
            request.setAttribute(WARN_VERIFICATION_PASSWORD_ERROR, "Verification code is invalid.");
            return VERIFICATION_PAGE;
        }
    }
}
