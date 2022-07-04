package epam.project.bookshop.service;

import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.ServiceException;

public interface AuthorService extends GenericService<Author> {
    boolean attachBookToAuthor(Long bookId, Long authorId) throws ServiceException;
}
