package epam.project.bookshop.service;

import epam.project.bookshop.dto.BookDto;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface BookService extends GenericService<BookDto> {
    List<BookDto> findBySearchingDetail(Map<String, String> searchMap) throws ServiceException;

    List<BookDto> findAllBookByBookName(String bookName) throws ServiceException;

    void changeQuantityByBookId(Long bookId, Long bookQuantity) throws ServiceException;
}
