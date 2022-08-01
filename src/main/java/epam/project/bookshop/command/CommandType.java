package epam.project.bookshop.command;

import epam.project.bookshop.command.impl.*;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.entity.User;
import epam.project.bookshop.validation.CommandValidation;

public enum CommandType {
    /* Authentication commands */
    LOGOUT(new LogoutCommand()),
    LOGIN(new LoginCommand()),
    DEFAULT(new DefaultCommand()),

    /* User commands */
    ADD_USER(new AddUserCommand()),
    DELETE_USER(new DeleteUserCommand()),
    UPDATE_USER(new UpdateUserCommand()),
    READ_USER(new FindAllUsersCommand()),
    FIND_USER_BY_ID(new FindUserByIdCommand()),
    CHECK_USER_TO_AUTHORIZATION(new CheckUserAuthorizationCommand()),
    GET_ACCESS_TO_USER_PROFILE(new GetAccessToProfileCommand()),
    FIND_USER_INFORMATION(new FindUserInformationCommand()),
    FIND_USER_TO_UPDATE_STATUS(new FindUserToUpdateStatus()),
    FIND_USER_ORDERS(new FindUserOrdersCommand()),
    UPDATE_USER_STATUS(new UpdateUserStatusCommand()),
    SEARCH_USER(new FindUserBySearchingDetailCommand()),
    SEND_PASSWORD_EMAIL(new EmailSenderCommand()),
    PASSWORD_VERIFICATION(new PasswordVerificationCommand()),
    PASSWORD_UPDATE(new UserPasswordUpdateCommand()),

    /* Book commands */
    ADD_BOOK(new AddBookCommand()),
    DELETE_BOOK(new DeleteBookCommand()),
    UPDATE_BOOK(new UpdateBookCommand()),
    READ_BOOK(new FindAllBooksCommand()),
    FIND_BOOK_BY_ID(new FindBookByIdCommand()),
    FIND_ALL_BOOKS_PAGE(new FindAllBooksPageCommand()),
    FIND_ONE_BOOK_INFORMATION(new FindBookInformationCommand()),
    ORDER_BOOK(new OrderBookCommand()),
    RATE_BOOK(new RateBookCommand()),
    SEARCH_BOOK(new FindBookBySearchDetailsCommand()),
    SEARCH_TO_MAIN_BOOK_PAGE(new FindBookByDetailsToPageCommand()),

    /* Author commands */
    ADD_AUTHOR(new AddAuthorCommand()),
    DELETE_AUTHOR(new DeleteAuthorCommand()),
    UPDATE_AUTHOR(new UpdateAuthorCommand()),
    READ_AUTHOR(new FindAllAuthorsCommand()),
    FIND_AUTHOR_BY_ID(new FindAuthorByIdCommand()),
    SEARCH_AUTHOR(new FindAuthorBySearchingDetails()),

    /* Genre commands */
    ADD_GENRE(new AddGenreCommand()),
    DELETE_GENRE(new DeleteGenreCommand()),
    UPDATE_GENRE(new UpdateGenreCommand()),
    READ_GENRE(new FindAllGenresCommand()),
    FIND_GENRE_BY_ID(new FindGenreByIdCommand()),
    SEARCH_GENRE(new FindGenreBySearchingDetails()),

    /* Order command */
    FIND_ALL_ORDERS(new FindAllOrdersCommand()),
    FIND_ALL_DELIVERED_ORDERS(new FindAllDeliveredCommand()),
    FIND_ALL_NOT_DELIVERED_ORDERS(new FindAllNotDeliveredCommand()),
    CHANGE_ORDER_DELIVERED_STATUS(new ChangeDeliveredStatusCommand()),
    SEARCH_ORDER(new FindOrdersBySearchingDetailsCommand()),

    CHANGE_LANGUAGE(new ChangeLanguageCommand());

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command castToCommand(String command) {
        CommandValidation commandValidation = new CommandValidation();
        boolean commandToValidation = commandValidation.checkCommandToValidation(command.trim());

        if (commandToValidation) {
            CommandType type = CommandType.valueOf(command.toUpperCase());
            return type.command;
        }else {
            return DEFAULT.command;
        }

    }

}
