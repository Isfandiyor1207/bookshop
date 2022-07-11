package epam.project.bookshop.command;

import epam.project.bookshop.command.impl.*;
import epam.project.bookshop.validation.CommandValidation;

public enum CommandType {
    // Authentication commands
    LOGOUT(new LogoutCommand()),
    LOGIN(new LoginCommand()),
    DEFAULT(new DefaultCommand()),

    // User commands
    ADD_USER(new AddUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    READ_USER(new FindAllUsersCommand()),
    FIND_USER_BY_ID(new FindUserByIdCommand()),

    // Book commands,
    ADD_BOOK(new AddBookCommand()),
    DELETE_BOOK(new DeleteBookCommand()),
    UPDATE_BOOK(new UpdateBookCommand()),
    READ_BOOK(new FindAllBooksCommand()),
    FIND_BOOK_BY_ID(new FindBookByIdCommand()),
    FIND_ALL_BOOKS_PAGE(new FindAllBooksPageCommand()),
    FIND_ONE_BOOK_INFORMATION(new FindBookInformationCommand()),
//    ORDER_BOOK(new OrderBookCommand()),

    // Author commands
    ADD_AUTHOR(new AddAuthorCommand()),
    DELETE_AUTHOR(new DeleteAuthorCommand()),
    UPDATE_AUTHOR(new UpdateAuthorCommand()),
    READ_AUTHOR(new FindAllAuthorsCommand()),
    FIND_AUTHOR_BY_ID(new FindAuthorByIdCommand()),

    // Genre commands
    ADD_GENRE(new AddGenreCommand()),
    DELETE_GENRE(new DeleteGenreCommand()),
    UPDATE_GENRE(new UpdateGenreCommand()),
    READ_GENRE(new FindAllGenresCommand()),
    FIND_GENRE_BY_ID(new FindGenreByIdCommand());

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command castToCommand(String command) {
        CommandValidation commandValidation = new CommandValidation();
        boolean commandToValidation = commandValidation.checkCommandToValidation(command);

        if (commandToValidation) {
            CommandType type = CommandType.valueOf(command.toUpperCase());
            return type.command;
        }else {
            return DEFAULT.command;
        }

    }

}
