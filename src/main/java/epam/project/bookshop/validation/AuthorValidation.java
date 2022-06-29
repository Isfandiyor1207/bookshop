package epam.project.bookshop.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static epam.project.bookshop.command.ParameterName.AUTHOR_FIO;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_AUTHOR_FULL_NAME;
import static epam.project.bookshop.validation.ValidationParameterName.WORN_AUTHOR_FULL_NAME;

public class AuthorValidation {
    private static final Logger logger = LogManager.getLogger();

    private static final String AUTHOR_FULL_NAME_REGEX = "[a-zA-Z]+";
    private static final AuthorValidation instance = new AuthorValidation();
    private static final BaseValidation baseValidation = BaseValidation.getInstance();

    private AuthorValidation() {
    }

    public static AuthorValidation getInstance() {
        return instance;
    }

    private boolean checkAuthorFirstnameToValidation(String fio) {
        boolean isValid = true;
        if (baseValidation.isEmpty(fio)) {
            logger.info("Author full name is empty.");
            isValid = false;
        }
        if (fio.matches(AUTHOR_FULL_NAME_REGEX)) {
            logger.info("Author full name is nor regaxed.");
            isValid = false;
        }
        return isValid;

//        return !baseValidation.isEmpty(fio) && fio.matches(AUTHOR_FULL_NAME_REGEX);
    }

    public boolean authorCreateValidation(Map<String, String> authorMap) {
        boolean isValid = true;

        if (!checkAuthorFirstnameToValidation(authorMap.get(AUTHOR_FIO).trim())) {
            logger.info("Author full name is not valid.");
            authorMap.put(WORN_AUTHOR_FULL_NAME, ERROR_AUTHOR_FULL_NAME);
            isValid = false;
        }

        return isValid;
    }


}
