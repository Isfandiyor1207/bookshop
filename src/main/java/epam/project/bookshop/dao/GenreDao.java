package epam.project.bookshop.dao;

import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends BaseDao<GenreDto, Genre>{
    Optional<GenreDto> findByName(String name) throws DaoException;

    void attachBookToGenre(Long bookId, Long genreId) throws DaoException;

    List<GenreDto> findAllByBookId(Long bookId) throws DaoException;

    List<Long> findAllGenreIdByBookId(Long bookId) throws DaoException;

    void deleteAttachedGenre(Long bookId, Long genreId) throws DaoException;

    List<Long> findAllBookIdByGenreId(Long genreId) throws DaoException;
}
