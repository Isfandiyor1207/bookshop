package epam.project.bookshop.dao;

import epam.project.bookshop.entity.Book;

import java.util.Optional;

public interface BookDao extends BaseDao<Book> {
    Optional<Book> findByName(String name);
}
