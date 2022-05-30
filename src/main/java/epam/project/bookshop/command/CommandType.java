package epam.project.bookshop.command;

import epam.project.bookshop.command.impl.*;

public enum CommandType {
    // Authentication commands
    LOGOUT(new LogoutCommand()),
    LOGIN(new LoginCommand()),
//    SIGNUP(new SignUpCommand()),

    // User commands
    ADD_USER(new AddUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),

    // Book commands
    ADD_BOOK(new AddBookCommand()),
    DELETE_BOOK(new DeleteBookCommand()),
    UPDATE_BOOK(new UpdateBookCommand()),

    // Author commands
    ADD_AUTHOR(new AddAuthorCommand()),
    DELETE_AUTHOR(new DeleteAuthorCommand()),
    UPDATE_AUTHOR(new UpdateAuthorCommand()),

    // Genre commands
    ADD_GENRE(new AddGenreCommand()),
    DELETE_GENRE(new DeleteGenreCommand()),
    UPDATE_GENRE(new UpdateGenreCommand());

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String command) {
        CommandType type = CommandType.valueOf(command.toUpperCase());
        return type.command;
    }

}
