package epam.project.bookshop.command;

public final class ParameterName {

    /* Unique parameters */
    public static final String ID = "id";
    public static final String DELETE_ID = "delete_by_id";
    public static final String UPDATE_BY_ID = "update_by_id";

    /* User parameters */
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PSW_REPEAT = "psw_repeat";
    public static final String PHONE_NUMBER = "contact";
    public static final String USER_ROLE_ID = "roleId";
    public static final String USER_ROLE_ID_IN_DB = "roleid";
    public static final String USER_PHONE_NUMBER_IN_DB = "phonenumber";
    public static final String USER_LIST = "user_list";

    /* Genre parameters */
    public static final String GENRE_NAME = "name";
    public static final String GENRE_ID = "genre_id";
    public static final String GENRE_LIST = "genre_list";

    /* Author parameters */
    public static final String AUTHOR_LIST = "author_list";
    public static final String AUTHOR_FIO = "fio";
    public static final String AUTHOR_ID = "author_id";

    /* Book parameters */
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_LIST = "book_list";
    public static final String BOOK_TOTAL = "total";
    public static final String BOOK_PRICE = "price";
    public static final String BOOK_PUBLISHING_YEAR = "publishing_year";
    public static final String BOOK_PUBLISHER_NAME = "publisher";
    public static final String BOOK_ISBN = "isbn";
    public static final String BOOK_NAME = "name";
    public static final String BOOK_DESCRIPTION = "description";
    public static final String BOOK_INFORMATION = "book_info";

    /* Attachment parameters */
    public static final String ATTACHMENT = "file";
    public static final String ATTACHMENT_CONTENT_TYPE = "file_content_type";
    public static final String ATTACHMENT_NAME = "attachment_name";
    public static final String ATTACHMENT_HASH_NAME = "hash_name";
    public static final String ATTACHMENT_ABSOLUTE_NAME = "absolute_name";
    public static final String ATTACHMENT_UPLOAD_PATH = "upload_path";
    public static final String ATTACHMENT_EXTENSION = "extension";


    private ParameterName() {
    }
}
