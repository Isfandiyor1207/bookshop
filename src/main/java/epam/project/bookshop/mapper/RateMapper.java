package epam.project.bookshop.mapper;

import com.oracle.wls.shaded.org.apache.regexp.RE;
import epam.project.bookshop.command.ParameterName;
import epam.project.bookshop.dto.RateDto;
import epam.project.bookshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class RateMapper implements BaseMapper<RateDto> {

    private static final Logger logger = LogManager.getLogger();
    private static final RateMapper instance=new RateMapper();

    private RateMapper(){}

    public static RateMapper getInstance() {
        return instance;
    }

    @Override
    public RateDto resultSetToDto(ResultSet resultSet) throws DaoException {
        RateDto rateDto=new RateDto();

        try {
            rateDto.setId(resultSet.getLong(ID));
            rateDto.setBookId(resultSet.getLong(BOOK_ID));
            rateDto.setUserId(resultSet.getLong(USER_ID));
            rateDto.setRate(resultSet.getLong(RATE_QUANTITY));
            return rateDto;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
