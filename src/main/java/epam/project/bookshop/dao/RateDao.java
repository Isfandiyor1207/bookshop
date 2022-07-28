package epam.project.bookshop.dao;

import epam.project.bookshop.dto.RateDto;
import epam.project.bookshop.entity.Rate;
import epam.project.bookshop.exception.DaoException;

import java.util.Optional;

public interface RateDao extends BaseDao<RateDto, Rate>{

    Optional<Double> getAverageRateByBookId(Long bookId) throws DaoException;

    Optional<Long> getNumberOfVotedUsersByBookId(Long bookId) throws DaoException;

    void deleteByBookId(Long bookId) throws DaoException;

    Optional<RateDto> findRateByBookIdAndUserId(Long bookId, Long userId) throws DaoException;
}
