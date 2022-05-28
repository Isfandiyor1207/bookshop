package epam.project.bookshop.service.impl;

import epam.project.bookshop.entity.User;
import epam.project.bookshop.service.UserService;
import lombok.Builder;

@Builder
public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return login.equals(password);
    }

    @Override
    public boolean addUser(User user) {

        return false;
    }
}
