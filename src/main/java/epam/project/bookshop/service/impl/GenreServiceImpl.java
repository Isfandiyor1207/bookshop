package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.GenreDao;
import epam.project.bookshop.dao.impl.GenreDaoImpl;
import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.GenreService;
import epam.project.bookshop.validation.GenreValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.GENRE_NAME;
import static epam.project.bookshop.command.ParameterName.ID;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class GenreServiceImpl implements GenreService {
    private static final Logger logger = LogManager.getLogger();
    private static final GenreServiceImpl instance = new GenreServiceImpl();
    private static final GenreDao genreDao = GenreDaoImpl.getInstance();
    private final GenreValidation genreValidation = GenreValidation.getInstance();

    private GenreServiceImpl() {
    }

    public static GenreServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return genreDao.deleteById(id);
        } catch (DaoException e) {
            logger.error("Genre does not deleted by this name!");
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean update(Map<String, String> genreMapper) throws ServiceException {
        if (!genreValidation.genreValidation(genreMapper)) {
            logger.info("Genre information is not valid!");
            return false;
        }
        logger.info("GENRE name: " + genreMapper.get(GENRE_NAME));
        logger.info("GENRE id: " + genreMapper.get(ID));

        boolean isUpdated = true;

        try {
            Optional<GenreDto> genreOptional = genreDao.findByName(genreMapper.get(GENRE_NAME));

            if (genreOptional.isPresent()) {
                genreMapper.put(WORN_GENRE_NAME, ERROR_GENRE_NAME_EXIST);
                isUpdated = false;
            }

        } catch (DaoException e) {
            logger.info("Genre by this name already available. " + e);
            throw new ServiceException(e);
        }

        try {
            // todo change id
            Optional<GenreDto> optionalGenre = genreDao.findById(Long.valueOf(genreMapper.get(ID))); // Long.valueOf(genreMapper.get(ID))
            if (optionalGenre.isPresent()) {
                isUpdated = genreDao.updated(genreMapper.get(GENRE_NAME), Long.valueOf(genreMapper.get(ID)));
            } else {
                logger.info("Genre is available in database");
                genreMapper.put(WORN_GENRE_NAME, ERROR_GENRE_NAME_IS_NOT_AVAILABLE);
            }
        } catch (DaoException e) {
            logger.info(e);
            throw new ServiceException(e);
        }

        return isUpdated;
    }

    @Override
    public List<GenreDto> findAll() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (DaoException e) {
            logger.error("Genre find all method is failed!");
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<GenreDto> findById(Long id) throws ServiceException {
        try {
            return genreDao.findById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> entity) throws ServiceException {
        if (!genreValidation.genreValidation(entity)) {
            logger.info("Genre information is not valid!");
            return false;
        }
        try {
            Optional<GenreDto> optionalGenre = genreDao.findByName(entity.get(GENRE_NAME));
            if (optionalGenre.isPresent()) {
                logger.info(optionalGenre.get() + " is available in database");
                entity.put(WORN_GENRE_NAME, ERROR_GENRE_NAME_EXIST);
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Genre genre = new Genre();
        genre.setName(entity.get(GENRE_NAME));

        try {
            return genreDao.save(genre) != null;
        } catch (DaoException e) {
            logger.error("Genre is not added to database.");
            throw new ServiceException(e);
        }

    }

    @Override
    public void attachBookToGenre(Long bookId, Long genreId, boolean isToUpdate) throws ServiceException {
        try {

            if (isToUpdate) {
                genreDao.deleteAttachedGenre(bookId, genreId);
            }
            genreDao.attachBookToGenre(bookId, genreId);

        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GenreDto> findAllByBookId(Long bookId) throws ServiceException {
        try {
            return genreDao.findAllByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
