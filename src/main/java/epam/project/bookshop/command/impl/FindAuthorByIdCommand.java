package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.AUTHOR_ID;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_AUTHOR_IS_NOT_FOUND;
import static epam.project.bookshop.validation.ValidationParameterName.WORN_AUTHOR_UPDATE;

public class FindAuthorByIdCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(AUTHOR_ID);

        logger.info("author id: " + id);
        AuthorService authorService = AuthorServiceImpl.getInstance();

        try {
            Optional<AuthorDto> optionalAuthor = authorService.findById(Long.valueOf(id));

            if (optionalAuthor.isPresent()) {
                logger.info("set session: " +  id);
                HttpSession session = request.getSession();
                session.setAttribute(AUTHOR_ID, id);
            } else {
                request.setAttribute(WORN_AUTHOR_UPDATE, ERROR_AUTHOR_IS_NOT_FOUND);
            }

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return WebPageName.AUTHOR_UPDATE_PAGE;
    }
}
