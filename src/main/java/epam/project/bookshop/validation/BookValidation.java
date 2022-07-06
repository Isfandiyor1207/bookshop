package epam.project.bookshop.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class BookValidation {
    private static final Logger logger = LogManager.getLogger();

    private static final String BOOK_NAME_REGEX = "[a-zA-Z0-9_.-]+";
    private static final String BOOK_STRING_REGEX = "[a-zA-Z]+";
    private static final String BOOK_NUMBER_REGEX = "^[0-9]+$";
    private static final BaseValidation baseValidation = BaseValidation.getInstance();
    private static final BookValidation instance = new BookValidation();

    private BookValidation() {
    }

    public static BookValidation getInstance() {
        return instance;
    }

    public boolean checkBookInformationToString(String data) {
        return !baseValidation.isEmpty(data);
    }

    public boolean checkBookInformationToNumber(String data) {
        return !baseValidation.isEmpty(data) && data.matches(BOOK_NUMBER_REGEX);
    }

    public boolean checkFileContentType(String fileContentType) {
        return fileContentType.substring(0, fileContentType.lastIndexOf("/")).equals("image");
    }

    public boolean validateBookInformation(Map<String, String> bookMap) {
        boolean isValid = true;

        if (!checkBookInformationToString(bookMap.get(BOOK_NAME).trim())) {
            logger.info("book name is not valid ");
            bookMap.put(WARN_BOOK_NAME, ERROR_BOOK_NAME);
            isValid = false;
        }

        if (!checkBookInformationToNumber(bookMap.get(BOOK_ISBN).trim())) {
            logger.info("book isbn is not valid ");
            bookMap.put(WARN_BOOK_ISBN, ERROR_BOOK_ISBN);
            isValid = false;
        }

        if (!checkBookInformationToString(bookMap.get(BOOK_PUBLISHER_NAME).trim())) {
            logger.info("book publisher name is not valid ");
            bookMap.put(WARN_BOOK_PUBLISHER_NAME, ERROR_BOOK_PUBLISHER_NAME);
            isValid = false;
        }

        if (!checkBookInformationToNumber(bookMap.get(BOOK_PUBLISHING_YEAR).trim())) {
            logger.info("book publishing year is not valid ");
            bookMap.put(WARN_BOOK_PUBLISHING_YEAR, ERROR_BOOK_PUBLISHING_YEAR);
            isValid = false;
        }

        if (!checkBookInformationToNumber(bookMap.get(BOOK_PRICE).trim())) {
            logger.info("book price is not valid ");
            bookMap.put(WARN_BOOK_PRICE, ERROR_BOOK_PRICE);
            isValid = false;
        }

        if (!checkBookInformationToNumber(bookMap.get(BOOK_TOTAL).trim())) {
            logger.info("book total is not valid ");
            bookMap.put(WARN_BOOK_TOTAL, ERROR_BOOK_TOTAL);
            isValid = false;
        }

        if (!checkBookInformationToString(bookMap.get(ATTACHMENT_NAME).trim())) {
            logger.info("attachment name is not valid ");
            bookMap.put(WARN_ATTACHMENT, ERROR_ATTACHMENT);
            isValid = false;
        }

        if (!checkBookInformationToString(bookMap.get(GENRE_ID).trim()) || bookMap.get(GENRE_ID).equals("0")) {
            logger.info("genre id is not valid ");
            bookMap.put(WARN_GENRE_ID, ERROR_GENRE_ID);
            isValid = false;
        }
        if (!checkBookInformationToString(bookMap.get(AUTHOR_ID).trim()) || bookMap.get(AUTHOR_ID).equals("0")) {
            logger.info("author id is not valid ");
            bookMap.put(WORN_AUTHOR_ID, ERROR_AUTHOR_ID);
            isValid = false;
        }
        if (!checkFileContentType(bookMap.get(ATTACHMENT_CONTENT_TYPE).trim())) {
            logger.info("image type is not correct ");
            bookMap.put(WARN_ATTACHMENT_CONTENT_TYPE, ERROR_ATTACHMENT_CONTENT_TYPE);
            isValid = false;
        }

        return isValid;
    }

    public boolean validationBookToUpdate(Map<String, String> bookMap, Map<String, String> query) {
        boolean isValid = true;

        if (checkBookInformationToString(bookMap.get(BOOK_NAME).trim())) {
            logger.info("book name is given to update ");
//            bookMap.put(WARN_BOOK_NAME, ERROR_BOOK_NAME);
            query.put(BOOK_NAME, bookMap.get(BOOK_NAME));
        }

        if (checkBookInformationToNumber(bookMap.get(BOOK_ISBN).trim())) {
            logger.info("book isbn is given to update ");
//            bookMap.put(WARN_BOOK_ISBN, ERROR_BOOK_ISBN);
            query.put(BOOK_ISBN, bookMap.get(BOOK_ISBN));
        }
        if (checkBookInformationToString(bookMap.get(BOOK_PUBLISHER_NAME).trim())) {
            logger.info("book publisher name is given to update ");
//            bookMap.put(WARN_BOOK_PUBLISHER_NAME, ERROR_BOOK_PUBLISHER_NAME);
            query.put(BOOK_PUBLISHER_NAME, bookMap.get(BOOK_PUBLISHER_NAME));
        }
        if (checkBookInformationToNumber(bookMap.get(BOOK_PUBLISHING_YEAR).trim())) {
            logger.info("book publishing year is given to update");
//            bookMap.put(WARN_BOOK_PUBLISHING_YEAR, ERROR_BOOK_PUBLISHING_YEAR);
            query.put(BOOK_PUBLISHING_YEAR, bookMap.get(BOOK_PUBLISHING_YEAR));
        }
        if (checkBookInformationToNumber(bookMap.get(BOOK_PRICE).trim())) {
            logger.info("book price is given to update");
//            bookMap.put(WARN_BOOK_PRICE, ERROR_BOOK_PRICE);
            query.put(BOOK_PRICE, bookMap.get(BOOK_PRICE));
        }
        if (checkBookInformationToNumber(bookMap.get(BOOK_TOTAL).trim())) {
            logger.info("book total is given to update");
//            bookMap.put(WARN_BOOK_TOTAL, ERROR_BOOK_TOTAL);
            query.put(BOOK_TOTAL, bookMap.get(BOOK_TOTAL));
        }

        if (checkBookInformationToString(bookMap.get(GENRE_ID).trim()) || bookMap.get(GENRE_ID).equals("0")) {
            logger.info("genre id is not valid ");
            bookMap.put(WARN_GENRE_ID, ERROR_GENRE_ID);
        }
        if (checkBookInformationToString(bookMap.get(AUTHOR_ID).trim()) || bookMap.get(AUTHOR_ID).equals("0")) {
            logger.info("author id is not valid ");
            bookMap.put(WORN_AUTHOR_ID, ERROR_AUTHOR_ID);
        }
        if (checkFileContentType(bookMap.get(ATTACHMENT_CONTENT_TYPE).trim())) {
            logger.info("image type is not correct ");
            bookMap.put(WARN_ATTACHMENT_CONTENT_TYPE, ERROR_ATTACHMENT_CONTENT_TYPE);
        }

        return isValid;
    }

}
