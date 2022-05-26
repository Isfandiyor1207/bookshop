package epam.project.bookshop.command;

import epam.project.bookshop.command.impl.AddBookCommand;
import epam.project.bookshop.command.impl.AddUserCommand;
import epam.project.bookshop.command.impl.LoginCommand;
import epam.project.bookshop.command.impl.LogoutCommand;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    ADD_BOOK(new AddBookCommand()),
    LOGOUT(new LogoutCommand()),
    LOGIN(new LoginCommand());

    // todo every command must be here -> There may be ADD_BOOK, DELETE_BOOK, ....

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String command) {
        CommandType type = CommandType.valueOf(command.toUpperCase());
        return type.command;
    }

}
