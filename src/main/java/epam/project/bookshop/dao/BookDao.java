package epam.project.bookshop.dao;

import epam.project.bookshop.entity.Book;
import epam.project.bookshop.exception.DaoException;

import java.util.Optional;

public interface BookDao extends BaseDao<Book> {
    Optional<Book> findByName(String name) throws DaoException;

    Long findIdByName(String name) throws DaoException;

    Optional<Book> findByISBN(String isbn) throws DaoException;
}
