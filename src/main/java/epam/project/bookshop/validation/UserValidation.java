package epam.project.bookshop.validation;

import epam.project.bookshop.util.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.*;

public class UserValidation {

    private static final Logger logger = LogManager.getLogger();

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final String PASSWORD_REGEX = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20})";
    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{12})$";
    private static final BaseValidation baseValidation = BaseValidation.getInstance();
    private static final UserValidation INSTANCE = new UserValidation();

    private UserValidation() {
    }

    public static UserValidation getInstance() {
        return INSTANCE;
    }

    public boolean checkEmailValidation(String email) {
        return !baseValidation.isEmpty(email) && email.matches(EMAIL_REGEX);
    }

    public boolean checkPasswordToValidation(String password) {
        return !baseValidation.isEmpty(password) && password.matches(PASSWORD_REGEX);
    }

    public boolean checkPhoneNumberToValidation(String phoneNumber) {
        return !baseValidation.isEmpty(phoneNumber) && phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public boolean userRegistrationValidation(Map<String, String> user) {
        boolean isValid = true;
        if (!checkEmailValidation(user.get(EMAIL).trim())) {
            user.put(WORN_EMAIL, ERROR_EMAIL_MSG);
            isValid = false;
        }
        if (!checkPasswordToValidation(user.get(PASSWORD).trim())) {
            user.put(WORN_PASSWORD, ERROR_PASSWORD_MSG);
            isValid = false;
        }
        if (!checkPhoneNumberToValidation(user.get(PHONE_NUMBER).trim())) {
            user.put(WORN_PHONE_NUMBER, ERROR_PHONE_NUMBER_MSG);
            isValid = false;
        }
        return isValid;
    }

    public boolean checkUpdateUser(Map<String, String> user, Map<String, String> query) {
        boolean isValid = false;

        logger.info("user info in validation: " + user);
        if (!baseValidation.isEmpty(user.get(FIRSTNAME))) {
            logger.info("User firstname is written to update: " + user.get(FIRSTNAME));
            query.put(FIRSTNAME, user.get(FIRSTNAME));
            isValid = true;
        }

        if (!baseValidation.isEmpty(user.get(LASTNAME))) {
            logger.info("User lastname is written to update: " + user.get(LASTNAME));
            query.put(LASTNAME, user.get(LASTNAME));
            isValid = true;
        }

        if (!baseValidation.isEmpty(user.get(USERNAME))) {
            logger.info("User username is written to update: " + user.get(USERNAME));
            query.put(USERNAME, user.get(USERNAME));
            isValid = true;
        }

        if (!baseValidation.isEmpty(user.get(PASSWORD))) {
            if (checkPasswordToValidation(user.get(PASSWORD))) {
                logger.info("User password is written to update: " + user.get(PASSWORD));
                query.put(PASSWORD, PasswordEncoder.getInstance().encode(user.get(PASSWORD)));
                isValid = true;
            } else {
                user.put(WORN_PASSWORD, ERROR_PASSWORD_MSG);
            }
        }

        if (!baseValidation.isEmpty(user.get(EMAIL))) {
            if (checkEmailValidation(user.get(EMAIL))) {
                logger.info("User email is written to update: " + user.get(EMAIL));
                query.put(EMAIL, user.get(EMAIL));
                isValid = true;
            } else {
                user.put(WORN_EMAIL, ERROR_EMAIL_MSG);
            }
        }

        if (!baseValidation.isEmpty(user.get(PHONE_NUMBER))) {
            if (checkPhoneNumberToValidation(user.get(PHONE_NUMBER))) {
                logger.info("user phone number is written to update:  " + user.get(PHONE_NUMBER));
                query.put(USER_PHONE_NUMBER_IN_DB, user.get(PHONE_NUMBER));
                isValid = true;
            } else {
                user.put(WORN_PHONE_NUMBER, ERROR_PHONE_NUMBER_MSG);
            }
        }

        logger.info("User update query: " + query);
        return isValid;
    }

    public void createSearchingQuery(Map<String, String> map, Map<String, String> query) {
        if (!baseValidation.isEmpty(map.get(FIRSTNAME))) {
            query.put(FIRSTNAME, "'%" + map.get(FIRSTNAME) + "%'");
            logger.info("query: " + query.get(FIRSTNAME));
        }
        if (!baseValidation.isEmpty(map.get(LASTNAME))) {
            query.put(LASTNAME, "'%" + map.get(LASTNAME) + "%'");
            logger.info("query: " + query.get(LASTNAME));
        }
        if (!baseValidation.isEmpty(map.get(USERNAME))) {
            query.put(USERNAME, "'%" + map.get(USERNAME) + "%'");
            logger.info("query: " + query.get(USERNAME));
        }
        if (!baseValidation.isEmpty(map.get(EMAIL))) {
            query.put(EMAIL, "'%" + map.get(EMAIL) + "%'");
            logger.info("query: " + query.get(EMAIL));
        }
        if (!baseValidation.isEmpty(map.get(PHONE_NUMBER))) {
            query.put(USER_PHONE_NUMBER_IN_DB, "'%" + map.get(PHONE_NUMBER) + "%'");
            logger.info("query: " + query.get(PHONE_NUMBER));
        }
    }

}