package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class BookMapper implements BaseMapper<BookDto> {
    private static final Logger logger = LogManager.getLogger();
    private static final BookMapper instance = new BookMapper();

    private BookMapper() {
    }

    public static BookMapper getInstance() {
        return instance;
    }

    @Override
    public BookDto resultSetToDto(ResultSet resultSet) throws DaoException {
        BookDto book = new BookDto();

        try {
            book.setId(resultSet.getLong(ID));
            book.setName(resultSet.getString(BOOK_NAME));
            book.setIsbn(resultSet.getString(BOOK_ISBN));
            book.setPublisher(resultSet.getString(BOOK_PUBLISHER_NAME));
            book.setPublishingYear(resultSet.getInt(BOOK_PUBLISHING_YEAR));
            book.setPrice(resultSet.getLong(BOOK_PRICE));
            book.setNumberOfBooks(resultSet.getLong(BOOK_TOTAL));
            book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }

        return book;
    }

}
