package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.AuthorDao;
import epam.project.bookshop.dao.impl.AuthorDaoImpl;
import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.validation.AuthorValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.AUTHOR_FIO;
import static epam.project.bookshop.command.ParameterName.ID;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class AuthorServiceImpl implements AuthorService {
    private static final Logger logger = LogManager.getLogger();
    private static final AuthorDao authorDao = AuthorDaoImpl.getInstance();
    private static final AuthorValidation authorValidation = AuthorValidation.getInstance();
    private static final AuthorServiceImpl instance = new AuthorServiceImpl();

    private AuthorServiceImpl() {

    }

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return authorDao.deleteById(id);
        } catch (DaoException e) {
            logger.error("Author not deleted by this id: " + id);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> authorUpdateMap) throws ServiceException {
        if (!authorValidation.authorCreateValidation(authorUpdateMap)) {
            logger.info("Author information is not valid!");
            return false;
        }
        logger.info("Author name: " + authorUpdateMap.get(AUTHOR_FIO));

        boolean isUpdated = true;

        try {
            Optional<AuthorDto> authorOptional = authorDao.findByFio(authorUpdateMap.get(AUTHOR_FIO));

            if (authorOptional.isPresent()) {
                authorUpdateMap.put(WORN_AUTHOR_FULL_NAME, ERROR_AUTHOR_FIO_EXIST);
                return false;
            }
        } catch (DaoException e) {
            logger.info("Author by this name already available. " + e);
            throw new ServiceException(e);
        }

        try {
            Optional<AuthorDto> authorOptional = authorDao.findById(Long.valueOf(authorUpdateMap.get(ID)));
            if (authorOptional.isPresent()) {
                isUpdated = authorDao.updated(authorUpdateMap.get(AUTHOR_FIO), Long.valueOf(authorUpdateMap.get(ID)));
            } else {
                logger.info("Author is not available");
                authorUpdateMap.put(WORN_AUTHOR_FULL_NAME, ERROR_AUTHOR_NAME_IS_NOT_AVAILABLE);
            }
        } catch (DaoException e) {
            logger.info(e);
            throw new ServiceException(e);
        }

        return isUpdated;
    }

    @Override
    public List<AuthorDto> findAll() throws ServiceException {

        try {
            return authorDao.findAll();
        } catch (DaoException e) {
            logger.info(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<AuthorDto> findById(Long id) throws ServiceException {
        try {
            return authorDao.findById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> authorMap) throws ServiceException {

        if (!authorValidation.authorCreateValidation(authorMap)) {
            logger.info("Author information is nor valid!");
            return false;
        }

        try {
            Optional<AuthorDto> authorOptional = authorDao.findByFio(authorMap.get(AUTHOR_FIO));
            if (authorOptional.isPresent()) {
                logger.info("Author by this fio already exists: " + authorOptional.get().getFio());
                authorMap.put(WORN_AUTHOR_FULL_NAME, ERROR_AUTHOR_FIO_EXIST);
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Author author = new Author();
        author.setFio(authorMap.get(AUTHOR_FIO).toLowerCase());

        try {
            return authorDao.save(author) != null;
        } catch (DaoException e) {
            logger.error("Author is not added. Error: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void attachBookToAuthor(Long bookId, Long authorId, boolean isToUpdate) throws ServiceException {
        try {
            if (isToUpdate) {
                authorDao.deleteAttachedAuthor(bookId, authorId);
            }
            authorDao.attachBookToAuthor(bookId, authorId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AuthorDto> findAllAuthorByBookId(Long bookId) throws ServiceException {
        try {
            return authorDao.findAllAuthorByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
