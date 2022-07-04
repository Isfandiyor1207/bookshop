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
//
//    public boolean checkBookNameToValidation(String name){
//        boolean isValid=true;
//        if (baseValidation.isEmpty(name)){
//            logger.info("empty data: " + name);
//            isValid = false;
//        }
//
//        return isValid;
//    }

    public boolean checkBookInformationToString(String data) {
//        boolean isValid=true;
//        if (baseValidation.isEmpty(data)){
//            logger.info("empty data: " + data);
//            isValid = false;
//        }
//
//        return isValid;

        return !baseValidation.isEmpty(data);
    }

    public boolean checkBookInformationToNumber(String data) {
//        boolean isValid=true;
//        if (baseValidation.isEmpty(data)){
//            logger.info("empty data: " + data);
//            isValid = false;
//        }
//
//        if (!data.matches(BOOK_NUMBER_REGEX)){
//            logger.info("regex is not valid: " + data);
//            isValid = false;
//        }
//
//        return isValid;

        return !baseValidation.isEmpty(data) && data.matches(BOOK_NUMBER_REGEX);
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
        return isValid;
    }

    public boolean validationBookToUpdate(Map<String, String> bookMap, Map<String, String> query){
        boolean isValid=true;

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

        return isValid;
    }

}
