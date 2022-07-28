package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.BookDao;
import epam.project.bookshop.dao.impl.BookDaoImpl;
import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AttachmentService;
import epam.project.bookshop.service.BookService;
import epam.project.bookshop.service.GenreService;
import epam.project.bookshop.util.Utils;
import epam.project.bookshop.validation.BaseValidation;
import epam.project.bookshop.validation.BookValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class BookServiceImpl implements BookService {
    private static final Logger logger = LogManager.getLogger();
    private static final BookServiceImpl instance = new BookServiceImpl();
    private static final BookDao bookDao = BookDaoImpl.getInstance();
    private static final AuthorServiceImpl authorService = AuthorServiceImpl.getInstance();
    private static final RateServiceImpl rateService = RateServiceImpl.getInstance();
    private static final GenreService genreService = GenreServiceImpl.getInstance();
    private static final BookValidation bookValidation = BookValidation.getInstance();
    private static final BaseValidation baseValidation = BaseValidation.getInstance();
    private static final AttachmentService attachmentService = AttachmentServiceImpl.getInstance();
    private static final Utils utils = new Utils();

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        // todo delete genre_list and author_list by book_id
        try {
            rateService.deleteByBookId(id);
            attachmentService.deleteByBookId(id);
            return bookDao.deleteById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> update) throws ServiceException {
        Map<String, String> query = new HashMap<>();

        logger.info(update);

        boolean isValid = bookValidation.validationBookToUpdate(update, query);

        if (!isValid) {
            logger.info("Book update values is not valid:");
            update.put(WARN_BOOK_UPDATE, ERROR_BOOK_UPDATE);
            return false;
        }

        if (baseValidation.isEmpty(update.get(BOOK_NAME))) {
            try {
                Optional<BookDto> optionalUser = bookDao.findByName(update.get(BOOK_NAME));
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

        StringBuilder stringBuilder = new StringBuilder();

        query.forEach((key, value) -> stringBuilder.append(key)
                .append("='")
                .append(value)
                .append("', "));


        String queryString = stringBuilder.toString();


        try {
            if (!queryString.isEmpty()) {
                queryString = queryString.substring(0, stringBuilder.length() - 2);
                bookDao.updated(queryString, Long.valueOf(update.get(ID)));
            }

            Long bookId = Long.valueOf(update.get(ID));

            String genreStringArray = update.get(GENRE_ID);

            if (!(genreStringArray != null && genreStringArray.equals("null"))) {

                List<Long> updateGenre = utils.convertStringArrayToList(genreStringArray);

                for (Long genreId : updateGenre) {
                    genreService.attachBookToGenre(bookId, genreId, true);
                }

            }

            String authorStringArray = update.get(AUTHOR_ID);

            if (!(authorStringArray != null && authorStringArray.equals("null"))) {

                List<Long> updateAuthor = utils.convertStringArrayToList(update.get(AUTHOR_ID));

                for (Long authorId : updateAuthor) {
                    authorService.attachBookToAuthor(bookId, authorId, true);
                }

            }

        } catch (DaoException e) {
            logger.info(e);
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<BookDto> findAll() throws ServiceException {

        try {
            List<BookDto> bookDtoList = bookDao.findAll();

            List<BookDto> bookList = new ArrayList<>();

            for (BookDto book : bookDtoList) {
                Long bookId = book.getId();
                book.setAuthorDtoList(authorService.findAllAuthorByBookId(bookId));
                book.setGenreDtoList(genreService.findAllByBookId(bookId));
                book.setAttachmentDtoList(attachmentService.findAllByBookId(bookId));
                book.setAverageRate(rateService.getAverageRateByBookId(bookId).get());
                book.setNumberOfVotedUser(rateService.getNumberOfVotedUsersByBookId(bookId).get());
                bookList.add(book);
            }

            return bookList;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<BookDto> findById(Long id) throws ServiceException {

        try {
            Optional<BookDto> optionalBookDto = bookDao.findById(id);
            BookDto bookDto = optionalBookDto.get();

            Long bookId = bookDto.getId();

            List<AuthorDto> authorDtoList = authorService.findAllAuthorByBookId(bookId);
            List<AttachmentDto> attachmentDtoList = attachmentService.findAllByBookId(bookId);
            List<GenreDto> genreDtoList = genreService.findAllByBookId(bookId);
            Double averageRate = rateService.getAverageRateByBookId(bookId).get();
            Long numberOfVotedUsers = rateService.getNumberOfVotedUsersByBookId(bookId).get();

            bookDto.setAuthorDtoList(authorDtoList);
            bookDto.setAttachmentDtoList(attachmentDtoList);
            bookDto.setGenreDtoList(genreDtoList);
            bookDto.setAverageRate(averageRate);
            bookDto.setNumberOfVotedUser(numberOfVotedUsers);

            return Optional.of(bookDto);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> bookMap) throws ServiceException {

        try {
            if (!bookValidation.validateBookInformation(bookMap)) {
                logger.error("Book data is not correct!");
                return false;
            }

            logger.info(bookMap.get(BOOK_NAME));

            Book book = new Book();

            book.setName(bookMap.get(BOOK_NAME));
            book.setIsbn(bookMap.get(BOOK_ISBN));
            book.setPublisher(bookMap.get(BOOK_PUBLISHER_NAME));
            book.setPublishingYear(Integer.parseInt(bookMap.get(BOOK_PUBLISHING_YEAR)));
            book.setPrice(Long.valueOf(bookMap.get(BOOK_PRICE)));
            book.setNumberOfBooks(Long.valueOf(bookMap.get(BOOK_TOTAL)));
            book.setDescription(bookMap.get(BOOK_DESCRIPTION));


            boolean isAdded = false;

            Optional<BookDto> daoByName = bookDao.findByName(bookMap.get(BOOK_NAME));

            if (daoByName.isPresent()) {
                logger.info("book is available by this name");
                bookMap.put(WARN_BOOK_NAME, ERROR_BOOK_IS_AVAILABLE_BY_NAME);
                return false;
            } else {
                logger.info("book is not available ");
            }

            Long bookId = bookDao.save(book);
            if (bookId != null) {

                List<Long> genreIdList = utils.convertStringArrayToList(bookMap.get(GENRE_ID));

                for (Long genreId : genreIdList) {
                    genreService.attachBookToGenre(bookId, genreId, false);
                }

                List<Long> authorIdList = utils.convertStringArrayToList(bookMap.get(AUTHOR_ID));

                for (Long authorId : authorIdList) {
                    authorService.attachBookToAuthor(bookId, authorId, false);
                }

                isAdded = true;
            } else {
                bookMap.put(WARN_BOOK_IS_NOT_ADDED, ERROR_BOOK_IS_NOT_ADDED);
            }
            return isAdded;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public Optional<BookDto> findByName(String name) throws ServiceException {
        try {
            Optional<BookDto> optionalBookDto = bookDao.findByName(name);
            BookDto bookDto = optionalBookDto.get();

            Long bookId = bookDto.getId();

            List<AuthorDto> authorDtoList = authorService.findAllAuthorByBookId(bookId);
            List<AttachmentDto> attachmentDtoList = attachmentService.findAllByBookId(bookId);
            List<GenreDto> genreDtoList = genreService.findAllByBookId(bookId);
            Double averageRate = rateService.getAverageRateByBookId(bookId).get();
            Long numberOfVotedUsers = rateService.getNumberOfVotedUsersByBookId(bookId).get();

            bookDto.setAuthorDtoList(authorDtoList);
            bookDto.setAttachmentDtoList(attachmentDtoList);
            bookDto.setGenreDtoList(genreDtoList);
            bookDto.setAverageRate(averageRate);
            bookDto.setNumberOfVotedUser(numberOfVotedUsers);
            return Optional.of(bookDto);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BookDto> findBySearchingDetail(Map<String, String> searchMap) throws ServiceException {

        List<BookDto> dtoList = new ArrayList<>();

        String bookName = searchMap.get(BOOK_NAME);
        String authorName = searchMap.get(AUTHOR_FIO);
        String genreId = searchMap.get(GENRE_ID);

        if (!bookName.isEmpty() && !authorName.isEmpty() && !genreId.equals("0")) {
            logger.info("!bookName.isEmpty() && !authorName.isEmpty() !genreId.equals(\"0\")");
            List<Long> bookIdList = genreService.findAllBookIdByGenreId(Long.valueOf(genreId));
            if (!bookIdList.isEmpty()) {

                List<Long> authorIdList = authorService.findAuthorIdByName(authorName);

                List<Long> bookIdListByAuthorId = new ArrayList<>();

                for (Long authorId : authorIdList) {
                    bookIdListByAuthorId.addAll(authorService.findAllBookIdByAuthorId(authorId));
                }

                List<BookDto> allBookByBookName = findAllBookByBookName(bookName);

                for (Long aLong : bookIdListByAuthorId) {
                    for (BookDto bookDto : allBookByBookName) {
                        if (Objects.equals(aLong, bookDto.getId())) {
                            dtoList.add(bookDto);
                        }
                    }
                }

                List<BookDto> bookList = new ArrayList<>();
                for (Long bookIdByGenre : bookIdList) {
                    for (BookDto bookDto : dtoList) {
                        if (Objects.equals(bookIdByGenre, bookDto.getId())) {
                            bookList.add(bookDto);
                        }
                    }
                }
                dtoList.clear();
                dtoList.addAll(bookList);
            }
        }

        if (bookName.isEmpty() && !authorName.isEmpty() && !genreId.equals("0")) {
            logger.info("bookName.isEmpty() && !authorName.isEmpty() && !genreId.equals(\"0\")");
            List<Long> bookIdList = genreService.findAllBookIdByGenreId(Long.valueOf(genreId));
            if (!bookIdList.isEmpty()) {
                List<Long> authorIdList = authorService.findAuthorIdByName(authorName);

                List<Long> bookIdListByAuthorId = new ArrayList<>();

                for (Long authorId : authorIdList) {
                    bookIdListByAuthorId.addAll(authorService.findAllBookIdByAuthorId(authorId));
                }

                for (Long bookId : bookIdListByAuthorId) {
                    Optional<BookDto> optionalBookDto = findById(bookId);
                    dtoList.add(optionalBookDto.get());
                }

                List<BookDto> bookList = new ArrayList<>();
                for (Long bookIdByGenre : bookIdList) {
                    for (BookDto bookDto : dtoList) {
                        if (Objects.equals(bookIdByGenre, bookDto.getId())) {
                            bookList.add(bookDto);
                        }
                    }
                }
                dtoList.clear();
                dtoList.addAll(bookList);
            }
        }

        if (!bookName.isEmpty() && authorName.isEmpty() && !genreId.equals("0")) {
            logger.info("!bookName.isEmpty() && authorName.isEmpty() && !genreId.equals(\"0\")");
            List<Long> bookIdByGenreList = genreService.findAllBookIdByGenreId(Long.valueOf(genreId));
            if (!bookIdByGenreList.isEmpty()) {
                List<BookDto> allBookByBookName = findAllBookByBookName(bookName);

                for (Long bookIdByGenre : bookIdByGenreList) {
                    for (BookDto bookDto : allBookByBookName) {
                        if (Objects.equals(bookIdByGenre, bookDto.getId())) {
                            dtoList.add(bookDto);
                        }
                    }
                }
            }
        }

        if (!bookName.isEmpty() && !authorName.isEmpty() && genreId.equals("0")) {
            logger.info("!bookName.isEmpty() && !authorName.isEmpty() && genreId.equals(\"0\")");

            List<Long> authorIdByName = authorService.findAuthorIdByName(authorName);

            List<Long> bookIdByAuthor = new ArrayList<>();

            for (Long authorId : authorIdByName) {
                bookIdByAuthor.addAll(authorService.findAllBookIdByAuthorId(authorId));
            }

            List<BookDto> bookDtoList = findAllBookByBookName(bookName);

            for (Long bookId : bookIdByAuthor) {
                for (BookDto bookDto : bookDtoList) {
                    if (Objects.equals(bookId, bookDto.getId())) {
                        dtoList.add(bookDto);
                    }
                }
            }

        }

        if (bookName.isEmpty() && authorName.isEmpty() && !genreId.equals("0")) {
            logger.info("bookName.isEmpty() && authorName.isEmpty() && !genreId.equals(\"0\")");
            List<Long> bookListByGenre = genreService.findAllBookIdByGenreId(Long.valueOf(genreId));
            for (Long bookId : bookListByGenre) {
                dtoList.add(findById(bookId).get());
            }
        }

        if (bookName.isEmpty() && !authorName.isEmpty() && genreId.equals("0")) {

            logger.info("bookName.isEmpty() && !authorName.isEmpty() && genreId.equals(\"0\")");

            List<Long> authorIdByName = authorService.findAuthorIdByName(authorName);
            List<Long> bookIdList = new ArrayList<>();
            for (Long authorId : authorIdByName) {
                bookIdList.addAll(authorService.findAllBookIdByAuthorId(authorId));
            }

            for (Long bookId : bookIdList) {
                dtoList.add(findById(bookId).get());
            }
        }

        if (!bookName.isEmpty() && authorName.isEmpty() && genreId.equals("0")) {
            logger.info("!bookName.isEmpty() && authorName.isEmpty() && genreId.equals(\"0\")");
            dtoList.addAll(findAllBookByBookName(bookName.trim()));
        }

        List<BookDto> bookList = new ArrayList<>();
        for (BookDto book : dtoList) {
            Long bookId = book.getId();
            book.setAuthorDtoList(authorService.findAllAuthorByBookId(bookId));
            book.setGenreDtoList(genreService.findAllByBookId(bookId));
            book.setAttachmentDtoList(attachmentService.findAllByBookId(bookId));
            book.setAverageRate(rateService.getAverageRateByBookId(bookId).get());
            book.setNumberOfVotedUser(rateService.getNumberOfVotedUsersByBookId(bookId).get());
            bookList.add(book);
        }

        return bookList;
    }

    @Override
    public List<BookDto> findAllBookByBookName(String bookName) throws ServiceException {
        try {
            return bookDao.findAllBookByBookName(bookName);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
