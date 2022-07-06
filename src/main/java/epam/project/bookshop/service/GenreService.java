package epam.project.bookshop.service;

import epam.project.bookshop.entity.Genre;
import epam.project.bookshop.exception.ServiceException;

public interface GenreService extends GenericService<Genre> {
    boolean attachBookToGenre(Long bookId, Long genreId, boolean isToUpdate) throws ServiceException;
}
