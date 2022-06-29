package epam.project.bookshop.dao;

import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.DaoException;

import java.util.Optional;

public interface AuthorDao extends BaseDao<Author> {

    Optional<Author> findByFio(String fio) throws DaoException;
}
