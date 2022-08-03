package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.RateDao;
import epam.project.bookshop.dao.impl.RateDaoImpl;
import epam.project.bookshop.dto.RateDto;
import epam.project.bookshop.entity.Rate;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.RateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static epam.project.bookshop.command.ParameterName.*;
import static epam.project.bookshop.validation.ValidationParameterName.ERROR_RATE_IS_NOT_AVAILABLE;
import static epam.project.bookshop.validation.ValidationParameterName.WARN_RATE_IS_NOT_FOUND;

public class RateServiceImpl implements RateService {

    private static final Logger logger = LogManager.getLogger();
    private static final RateDao rateDao = RateDaoImpl.getInstance();
    private static final RateServiceImpl instance = new RateServiceImpl();

    private RateServiceImpl(){}

    public static RateServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        try {
            return rateDao.deleteById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> rateMap) throws ServiceException {
        Long userId = Long.valueOf(rateMap.get(USER_ID));
        Long bookId = Long.valueOf(rateMap.get(BOOK_ID));
        Long rateQuantity = Long.valueOf(rateMap.get(BOOK_RATE));

        Rate rate = new Rate();
        rate.setBookId(bookId);
        rate.setUserId(userId);
        rate.setRate(rateQuantity);

        try {

            Optional<RateDto> optionalRateDto = rateDao.findRateByBookIdAndUserId(bookId, userId);

            if (optionalRateDto.isPresent()) {
                return rateDao.updated(String.valueOf(rateQuantity), optionalRateDto.get().getId());
            } else {
                rateMap.put(WARN_RATE_IS_NOT_FOUND, ERROR_RATE_IS_NOT_AVAILABLE);
                return false;
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RateDto> findAll() throws ServiceException {
        try {
            return rateDao.findAll();
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<RateDto> findById(Long id) throws ServiceException {
        try {
            return rateDao.findById(id);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(Map<String, String> rateMap) throws ServiceException {

        Long userId = Long.valueOf(rateMap.get(USER_ID));
        Long bookId = Long.valueOf(rateMap.get(BOOK_ID));
        Long rateQuantity = Long.valueOf(rateMap.get(BOOK_RATE));

        Rate rate = new Rate();
        rate.setBookId(bookId);
        rate.setUserId(userId);
        rate.setRate(rateQuantity);

        try {
            Optional<RateDto> optionalRateDto = rateDao.findRateByBookIdAndUserId(bookId, userId);

            boolean isAdded;
            if (optionalRateDto.isPresent()) {
                isAdded = rateDao.updated(String.valueOf(rateQuantity), optionalRateDto.get().getId());
            } else {
                Long save = rateDao.save(rate);
                isAdded = save > 0;
            }

            return isAdded;
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Double> getAverageRateByBookId(Long bookId) throws ServiceException {
        try {
            return rateDao.getAverageRateByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Long> getNumberOfVotedUsersByBookId(Long bookId) throws ServiceException {

        try {
            return rateDao.getNumberOfVotedUsersByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByBookId(Long bookId) throws ServiceException {
        try {
            rateDao.deleteByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
