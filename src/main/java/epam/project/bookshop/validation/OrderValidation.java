package epam.project.bookshop.validation;

import epam.project.bookshop.command.ParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static epam.project.bookshop.validation.ValidationParameterName.ERROR_BOOK_QUANTITY;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_BOOK_QUANTITY;

public class OrderValidation {

    private static final Logger logger = LogManager.getLogger();
    private static final OrderValidation instance = new OrderValidation();
    private static final BaseValidation baseValidation = BaseValidation.getInstance();

    private static final String REGEX_NUMBER = "[1-9][0-9]*";

    public static OrderValidation getInstance() {
        return instance;
    }

    private boolean checkBookQuantity(String quantity) {
        return !baseValidation.isEmpty(quantity) && quantity.matches(REGEX_NUMBER);
    }

    public boolean validateOrderedBookInformation(Map<String, String> orderedBookMap) {

        boolean isValid = true;

        if (!checkBookQuantity(orderedBookMap.get(ParameterName.BOOK_ORDER_QUANTITY))) {
            logger.info("Book quantity is not correct.");
            orderedBookMap.put(WARN_BOOK_QUANTITY, ERROR_BOOK_QUANTITY);
            isValid = false;
        }
        return isValid;
    }

}
