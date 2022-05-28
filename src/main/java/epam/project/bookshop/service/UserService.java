package epam.project.bookshop.service;

import epam.project.bookshop.entity.User;

public interface UserService {
    boolean authenticate(String login, String password);

    boolean addUser(User user);
}
