package epam.project.bookshop.service.impl;

import epam.project.bookshop.service.AuthService;

public class AuthServiceImpl implements AuthService {
    private static final AuthServiceImpl instance = new AuthServiceImpl();

    private AuthServiceImpl() {
    }

    public static AuthServiceImpl getInstance() {
        return instance;
    }
}
