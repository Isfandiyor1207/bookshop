package epam.project.bookshop.service.impl;

import epam.project.bookshop.service.BookService;

public class BookServiceImpl implements BookService {
    private static final BookServiceImpl instance = new BookServiceImpl();

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        return instance;
    }
}
