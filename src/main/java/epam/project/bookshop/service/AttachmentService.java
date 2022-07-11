package epam.project.bookshop.service;

import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.ServiceException;

import java.util.List;

public interface AttachmentService extends GenericService<AttachmentDto>{
    void deleteByBookId(Long bookId) throws ServiceException;

    List<AttachmentDto> findAllByBookId(Long bookId) throws ServiceException;
}
