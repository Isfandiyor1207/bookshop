package epam.project.bookshop.dao;

import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao extends BaseDao<BookDto, Book> {
    Optional<BookDto> findByName(String name) throws DaoException;

    Long findIdByName(String name) throws DaoException;

    Optional<BookDto> findByISBN(String isbn) throws DaoException;

    List<BookDto> findAllBookByBookName(String bookName) throws DaoException;

    void changeQuantityByBookId(Long bookId, Long bookQuantity) throws DaoException;
}
