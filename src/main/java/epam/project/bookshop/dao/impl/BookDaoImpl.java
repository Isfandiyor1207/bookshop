package epam.project.bookshop.dao.impl;

import epam.project.bookshop.dao.BookDao;
import epam.project.bookshop.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean save(Book book) {
        return false;
    }

    @Override
    public boolean updated(Book book) {
        return false;
    }

    @Override
    public boolean deleteById(Book book) {
        return false;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Optional<Book> findByName(String name) {
        return Optional.empty();
    }
}
