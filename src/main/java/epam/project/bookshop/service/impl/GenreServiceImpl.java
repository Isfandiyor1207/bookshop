package epam.project.bookshop.service.impl;

import epam.project.bookshop.service.GenreService;

public class GenreServiceImpl implements GenreService {
    private static final GenreServiceImpl instance = new GenreServiceImpl();

    private GenreServiceImpl() {
    }

    public static GenreServiceImpl getInstance() {
        return instance;
    }
}
