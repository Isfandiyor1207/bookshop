package epam.project.bookshop.dao;

import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends BaseDao<AuthorDto, Author> {

    Optional<AuthorDto> findByFio(String fio) throws DaoException;

    void attachBookToAuthor(Long bookId, Long authorId) throws DaoException;

    List<AuthorDto> findAllAuthorByBookId(Long bookId) throws DaoException;

    List<Long> findListOfAuthorIdByBookId(Long bookId) throws DaoException;

    void deleteAttachedAuthor(Long bookId, Long authorId) throws DaoException;

    List<Long> findAuthorIdByName(String authorName) throws DaoException;

    List<Long> findAllBookIdByAuthorId(Long authorId) throws DaoException;
}
