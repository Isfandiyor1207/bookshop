package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.command.WebPageName;
import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.exception.CommandException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.service.impl.AuthorServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static epam.project.bookshop.command.ParameterName.AUTHOR_FIO;
import static epam.project.bookshop.command.WebPageName.*;

public class FindAuthorBySearchingDetails implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String fio = request.getParameter(AUTHOR_FIO);
        AuthorService authorService= AuthorServiceImpl.getInstance();

        try {
            List<AuthorDto> authorDtoList = authorService.findBySearchingFio(fio);
            request.setAttribute(ParameterName.AUTHOR_LIST, authorDtoList);
        } catch (ServiceException e) {
            logger.error("Authors not found. " + e);
            throw new CommandException(e);
        }

        return AUTHOR_PAGE;
    }
}
