package epam.project.bookshop.command;

import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import org.checkerframework.checker.index.qual.PolyUpperBound;

import java.lang.reflect.Field;

public final class ParameterName {

    /* Unique parameters */
    public static final String ID = "id";
    public static final String DELETE_ID = "delete_by_id";
    public static final String UPDATE_BY_ID = "update_by_id";
    public static final String COMMAND = "command";
    public static final String CURRENT_LOCALE = "locale";
    public static final String CURRENT_PAGE = "current_page";

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
    public static final String USER_INFO = "user_info";
    public static final String AUTH_USER = "auth_user";
    public static final String VERIFICATION_CODE = "verification_code";
    public static final String VERIFICATION_SUCCESS = "verification_success";

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
    public static final String BOOK_RATE = "rate";

    /* Attachment parameters */
    public static final String ATTACHMENT = "file";
    public static final String ATTACHMENT_CONTENT_TYPE = "file_content_type";
    public static final String ATTACHMENT_NAME = "attachment_name";
    public static final String ATTACHMENT_HASH_NAME = "hash_name";
    public static final String ATTACHMENT_ABSOLUTE_NAME = "absolute_name";
    public static final String ATTACHMENT_UPLOAD_PATH = "upload_path";
    public static final String ATTACHMENT_EXTENSION = "extension";

    /* Rate parameters */
    public static final String RATE_QUANTITY = "rate";

    /* Order parameters */
    public static final String BOOK_ORDER_QUANTITY = "quantity";
    public static final String BOOK_DELIVERED = "delivered";
    public static final String ORDERS_BY_USER_ID = "user_order";
    public static final String ORDER_PRICE = "order_price";
    public static final String ALL_ORDERS = "all_orders";
    public static final String ALL_ORDERS_BY_STATUS = "all_orders_by_status";
    public static final String DELIVERED_STATUS = "order_delivered_status";


    private ParameterName() {
    }
}
