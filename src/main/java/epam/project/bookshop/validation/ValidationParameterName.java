package epam.project.bookshop.validation;

import epam.project.bookshop.entity.User;

public final class ValidationParameterName {

    /* User WORN */
    public static final String WORN_EMAIL = "email_error";
    public static final String WORN_PHONE_NUMBER = "phone_number_error";
    public static final String WORN_PASSWORD = "password_error";
    public static final String WRONG_PSW_REPEATED = "psw_repeated_error";
    public static final String WORN_USERNAME = "username_error";
    public static final String WORN_DELETED = "deleted_error";
    public static final String WORN_LOGIN = "login_error";
    public static final String WORN_USER = "user_error";

    /* User ERROR */
    public static final String ERROR_EMAIL_MSG = "Email is incorrect!";
    public static final String ERROR_PHONE_NUMBER_MSG = "Phone number is incorrect!";
    public static final String ERROR_PASSWORD_MSG = "Password is incorrect!";
    public static final String ERROR_PSW_REPEATED_MSG = "Repeated password is not the same!";
    public static final String ERROR_USERNAME_MSG = "User by this username already exists!";
    public static final String ERROR_USER_NOT_DELETED_MSG = "User is not deleted";
    public static final String ERROR_USER_NOT_EXIST_MSG = "User is not exist.";
    public static final String ERROR_LOGIN_MSG = "Password is incorrect!";

    /* GENRE WORN */
    public static final String WORN_GENRE_NAME="genre_name_error";

    /* GENRE ERROR */
    public static final String ERROR_GENRE_NAME="Genre name is not correct!";
    public static final String ERROR_GENRE_NAME_EXIST="Genre by this name already existed!";
    public static final String ERROR_GENRE_NAME_IS_NOT_AVAILABLE="Genre by this id is not available.";
    public static final String ERROR_GENRE_NOT_DELETED_MSG = "Genre is not deleted";

    /* Author WORN */
    public static final String WORN_AUTHOR_FULL_NAME = "author_full_name_error";

    /* Author WORN */
    public static final String ERROR_AUTHOR_FULL_NAME= "Author full name is empty!";
    public static final String ERROR_AUTHOR_NOT_DELETED_MSG = "Author is not deleted";
    public static final String ERROR_AUTHOR_FIO_EXIST="Author by this name already existed!";
    public static final String ERROR_AUTHOR_NAME_IS_NOT_AVAILABLE="Author by this id is not available.";

    /* Book WARN */
    public static final String WARN_BOOK_NAME = "book_name_error";
    public static final String WARN_BOOK_ISBN = "book_isbn_error";
    public static final String WARN_BOOK_PUBLISHER_NAME = "book_publisher_name_error";
    public static final String WARN_BOOK_PUBLISHING_YEAR = "book_publishing_year_error";
    public static final String WARN_BOOK_PRICE = "book_price_error";
    public static final String WARN_BOOK_TOTAL = "book_total_error";
    public static final String WARN_BOOK_IS_NOT_ADDED = "book_added_error";
    public static final String WARN_BOOK_IS_AVAILABLE_BY_ISBN = "book_isbn_exists_error";
    public static final String WARN_BOOK_IS_AVAILABLE_BY_NAME = "book_name_exists_error";

    /* Book ERROR */
    public static final String ERROR_BOOK_NAME = "Book name is empty!";
    public static final String ERROR_BOOK_ISBN = "Book isbn is empty!";
    public static final String ERROR_BOOK_PUBLISHER_NAME = "Book publisher is empty!";
    public static final String ERROR_BOOK_PUBLISHING_YEAR = "Book publishing year is empty!";
    public static final String ERROR_BOOK_PRICE = "Book price is empty!";
    public static final String ERROR_BOOK_TOTAL = "Book total is empty!";
    public static final String ERROR_BOOK_IS_NOT_ADDED = "Book is not added!";
    public static final String ERROR_BOOK_IS_NOT_DELETED = "Book is not deleted!";
    public static final String ERROR_BOOK_IS_AVAILABLE_BY_ISBN = "Book is already exists by this isbn!";
    public static final String ERROR_BOOK_IS_AVAILABLE_BY_NAME = "Book is already exists by this name!";

}
