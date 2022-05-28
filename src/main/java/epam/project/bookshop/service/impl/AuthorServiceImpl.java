package epam.project.bookshop.service.impl;

import epam.project.bookshop.service.AuthorService;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorServiceImpl instance = new AuthorServiceImpl();

    private AuthorServiceImpl(){

    }

    public static AuthorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String username, String login) {
        return false;
    }
}
