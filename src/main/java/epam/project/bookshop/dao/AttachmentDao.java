package epam.project.bookshop.dao;

import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.DaoException;

import java.util.List;

public interface AttachmentDao extends BaseDao<AttachmentDto, Attachment> {

    void attachFileToBook(Long bookId, Long fileId) throws DaoException;

    List<Long> findAllAttachmentIdByBookId(Long id) throws DaoException;

    List<AttachmentDto> findAllByBookId(Long bookId) throws DaoException;

    void deleteAttachedFile(Long bookId, Long attachmentId) throws DaoException;

}
