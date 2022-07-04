package epam.project.bookshop.dao;

import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    boolean save(T t) throws DaoException;

    boolean updated(String query, Long id) throws DaoException;

    boolean deleteById(Long id) throws DaoException, ServiceException;

    Optional<T> findById(Long id) throws DaoException;

    List<T> findAll() throws DaoException;

}
