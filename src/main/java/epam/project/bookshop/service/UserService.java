package epam.project.bookshop.service;

import epam.project.bookshop.entity.User;

public interface UserService extends GenericService<User> {
    boolean authenticate(String login, String password);

}
