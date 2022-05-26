package epam.project.bookshop.service;

public interface UserService {
    boolean authenticate(String login, String password);
}
