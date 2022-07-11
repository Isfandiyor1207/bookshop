package epam.project.bookshop.dao;

import epam.project.bookshop.dto.GenericDto;
import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.BaseDomain;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/*
* T - Dto
* E - Entity
* */

public interface BaseDao<T extends GenericDto, E extends BaseDomain> {
    Long save(E e) throws DaoException;

    boolean updated(String query, Long id) throws DaoException;

    boolean deleteById(Long id) throws DaoException, ServiceException;

    Optional<T> findById(Long id) throws DaoException;

    List<T> findAll() throws DaoException;

}
