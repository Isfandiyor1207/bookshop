package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.GenreService;
import epam.project.bookshop.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.GENRE_ID;
import static epam.project.bookshop.command.WebPageName.*;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_GENRE_IS_NOT_FOUND;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_GENRE_UPDATE;

public class FindGenreByIdCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String id = request.getParameter(GENRE_ID);
        GenreService genericService = GenreServiceImpl.getInstance();

        try {
            Optional<GenreDto> optionalGenre = genericService.findById(Long.valueOf(id));

            if (optionalGenre.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute(GENRE_ID, id);
            } else {
                request.setAttribute(WARN_GENRE_UPDATE, ERROR_GENRE_IS_NOT_FOUND);
            }

        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        return GENRE_UPDATE_PAGE;
    }
}
