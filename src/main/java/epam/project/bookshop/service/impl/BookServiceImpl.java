package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.BookDao;
import epam.project.bookshop.dao.impl.BookDaoImpl;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AttachmentService;
import epam.project.bookshop.service.AuthorService;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.GenreService;
import epam.project.bookshop.util.Utils;
import epam.project.bookshop.validation.BaseValidation;
import epam.project.bookshop.validation.BookValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class BookServiceImpl implements BookService {
    private static final Logger logger = LogManager.getLogger();
    private static final BookServiceImpl instance = new BookServiceImpl();
    private static final BookDao bookDao = BookDaoImpl.getInstance();
    private static final AuthorService authorService = AuthorServiceImpl.getInstance();
    private static final BookValidation bookValidation = BookValidation.getInstance();
    private static final BaseValidation baseValidation = BaseValidation.getInstance();
    private static final AttachmentService attachmentService = AttachmentServiceImpl.getInstance();
    private static final GenreService genreService = GenreServiceImpl.getInstance();

    private static Utils utils = new Utils();

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return bookDao.deleteById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> update) throws ServiceException {
        Map<String, String> query = new HashMap<>();

        boolean isValid = bookValidation.validationBookToUpdate(update, query);

        if (!isValid) {
            logger.info("user update values is not valid:");
            return false;
        }

        if (baseValidation.isEmpty(update.get(BOOK_NAME))) {
            try {
                Optional<Book> optionalUser = bookDao.findByName(update.get(USERNAME));
                if (optionalUser.isPresent()) {
                    update.put(WARN_BOOK_IS_AVAILABLE_BY_NAME, ERROR_BOOK_IS_AVAILABLE_BY_NAME);
                    logger.info(WARN_BOOK_IS_AVAILABLE_BY_NAME + ": " + ERROR_BOOK_IS_AVAILABLE_BY_NAME);
                    return false;
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }

        if (baseValidation.isEmpty(update.get(BOOK_ISBN))) {
            try {
                Optional<Book> optionalUser = bookDao.findByISBN(update.get(BOOK_ISBN));
                if (optionalUser.isPresent()) {
                    update.put(WARN_BOOK_IS_AVAILABLE_BY_ISBN, ERROR_BOOK_IS_AVAILABLE_BY_ISBN);
                    logger.info(WARN_BOOK_IS_AVAILABLE_BY_ISBN + ": " + ERROR_BOOK_IS_AVAILABLE_BY_ISBN);
                    return false;
                }
            } catch (DaoException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        query.forEach((key, value) -> stringBuilder.append(key)
                .append("='")
                .append(value)
                .append("', "));

        logger.info("Query: " + query);

        String queryString = stringBuilder.toString();

        if (!queryString.isEmpty()){
            queryString = queryString.substring(0, stringBuilder.length() - 2);
        }

        if (update.get(GENRE_ID) != null){
            List<Long> updateGenre = utils.convertStringArrayToList(update.get(GENRE_ID));

            updateGenre.forEach(id -> {
                try {

                    genreService.attachBookToGenre(Long.valueOf(update.get(ID)), id, true);

                } catch (ServiceException e) {

                    e.printStackTrace();
                }
            });

        }

        try {
            return bookDao.updated(queryString, Long.valueOf(update.get(ID)));
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Book> findAll() throws ServiceException {

        try {
            return bookDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) throws ServiceException {

        try {
            return bookDao.findById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> bookMap) throws ServiceException {

        if (!bookValidation.validateBookInformation(bookMap)) {
            logger.error("Book data is not correct!");
            return false;
        }

        logger.info(bookMap.get(BOOK_NAME));

        try {
            Optional<Book> daoByName = bookDao.findByName(bookMap.get(BOOK_NAME));

            if (daoByName.isPresent()) {
                logger.info("book is available by this name");
                bookMap.put(WARN_BOOK_NAME, ERROR_BOOK_IS_AVAILABLE_BY_NAME);
                return false;
            } else {
                logger.info("book is not available ");
            }

        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        Book book = new Book();

        book.setName(bookMap.get(BOOK_NAME));
        book.setIsbn(bookMap.get(BOOK_ISBN));
        book.setPublisher(bookMap.get(BOOK_PUBLISHER_NAME));
        book.setPublishingYear(Integer.parseInt(bookMap.get(BOOK_PUBLISHING_YEAR)));
        book.setPrice(Long.valueOf(bookMap.get(BOOK_PRICE)));
        book.setNumberOfBooks(Long.valueOf(bookMap.get(BOOK_TOTAL)));


        boolean isAdded = false;

        try {
            if (bookDao.save(book)) {

                Long bookId = bookDao.findIdByName(book.getName().toLowerCase());

                List<Long> genreIds = utils.convertStringArrayToList(bookMap.get(GENRE_ID));

                genreIds.forEach(id -> {
                    try {
                        genreService.attachBookToGenre(bookId, id, false);
                    } catch (ServiceException e) {

                        // todo what type of error I must throw

                        logger.error(e);
                    }
                });

                List<Long> authorIds = utils.convertStringArrayToList(bookMap.get(AUTHOR_ID));

                authorIds.forEach(id -> {
                    try {
                        authorService.attachBookToAuthor(bookId, id);
                    } catch (ServiceException e) {
                        logger.error(e);
                    }
                });

                isAdded = true;
            } else {
                bookMap.put(WARN_BOOK_IS_NOT_ADDED, ERROR_BOOK_IS_NOT_ADDED);
            }

        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

        return isAdded;
    }
}
