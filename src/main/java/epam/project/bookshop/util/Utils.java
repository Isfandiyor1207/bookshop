package epam.project.bookshop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private static final Logger logger = LogManager.getLogger();

    public List<Long> convertStringArrayToList(String array) {

        List<Long> list;

        array = array.substring(1, array.length()-1);
        logger.info("array: : " + array);

        list = Arrays.stream(array.split(", ")).map(Long::valueOf).collect(Collectors.toList());

        logger.info("Array :" + list);

        return list;
    }

}
