package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.UserDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.UserService;
import epam.project.bookshop.service.impl.SendEmailService;
import epam.project.bookshop.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.AUTH_USER;
import static epam.project.bookshop.command.ParameterName.USERNAME;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class EmailSenderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            String username = request.getParameter(USERNAME);

            logger.info("Username for verification: " + username);

            UserService userService = UserServiceImpl.getInstance();

            Optional<UserDto> optionalUserDto = userService.findUserByUsername(username);

            String page;
            if (optionalUserDto.isPresent()) {

                SendEmailService sendEmail = SendEmailService.getInstance();

                String password = sendEmail.generatePassword();

                UserDto userDto = new UserDto();
                userDto.setCode(password);
                userDto.setUsername(username);
                userDto.setId(optionalUserDto.get().getId());
                userDto.setEmail(optionalUserDto.get().getEmail());

                boolean isSend = sendEmail.sendEmail(userDto);

                if (isSend) {
                    HttpSession session = request.getSession();
                    session.setAttribute(AUTH_USER, userDto);
                    page = WebPageName.VERIFICATION_PAGE;
                } else {
                    request.setAttribute(WARN_USER_PASSWORD_CHANGING, ERROR_USER_PASSWORD_CHANGING);
                    page = WebPageName.PASSWORD_CHANGING_PAGE;
                }
            } else {
                request.setAttribute(WORN_USER, ERROR_USER_NOT_EXIST_MSG);
                page = WebPageName.PASSWORD_CHANGING_PAGE;
            }
            return page;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
