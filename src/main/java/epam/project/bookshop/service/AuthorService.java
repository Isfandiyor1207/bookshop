package epam.project.bookshop.service;

import epam.project.bookshop.dto.AuthorDto;
import epam.project.bookshop.entity.Author;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;

public interface AuthorService extends GenericService<AuthorDto> {
    void attachBookToAuthor(Long bookId, Long authorId, boolean isToUpdate) throws ServiceException;

    List<AuthorDto> findAllAuthorByBookId(Long bookId) throws ServiceException;
}
