package epam.project.bookshop.service;

import epam.project.bookshop.dto.GenreDto;
import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;

public interface GenreService extends GenericService<GenreDto> {
    void attachBookToGenre(Long bookId, Long genreId, boolean isToUpdate) throws ServiceException;

    List<GenreDto> findAllByBookId(Long bookId) throws ServiceException;

    List<Long> findAllBookIdByGenreId(Long genreId) throws ServiceException;

    List<GenreDto> findGenreBySearchingDetail(String genreName) throws ServiceException;

    void deleteGenreListByBookId(Long bookId) throws ServiceException;
}
