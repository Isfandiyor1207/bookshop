package epam.project.bookshop.service;

import epam.project.bookshop.dto.RateDto;
import epam.project.bookshop.exception.ServiceException;

import java.util.Optional;

public interface RateService extends GenericService<RateDto> {

    Optional<Double> getAverageRateByBookId(Long bookId) throws ServiceException;

    Optional<Long> getNumberOfVotedUsersByBookId(Long bookId) throws ServiceException;

    void deleteByBookId(Long bookId) throws ServiceException;

}
