package epam.project.bookshop.service.impl;

import epam.project.bookshop.dao.AttachmentDao;
import epam.project.bookshop.dao.impl.AttachmentDaoImpl;
import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.DaoException;
import epam.project.bookshop.exception.ServiceException;
import epam.project.bookshop.service.AttachmentService;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static epam.project.bookshop.command.ParameterName.ATTACHMENT_UPLOAD_DIRECTORY;
import static epam.project.bookshop.command.ParameterName.PROPERTY_CONFIG;

public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger logger = LogManager.getLogger();
    private static final AttachmentServiceImpl instance = new AttachmentServiceImpl();
    private static final AttachmentDao attachmentDao = AttachmentDaoImpl.getInstance();
    private static String UPLOAD_DIRECTORY;

    private AttachmentServiceImpl(){
        try (InputStream input = SendEmailService.class.getClassLoader().getResourceAsStream(PROPERTY_CONFIG)) {
            Properties prop = new Properties();
            prop.load(input);
            UPLOAD_DIRECTORY = prop.getProperty("upload.url");
        }catch (IOException ex) {
            logger.error(ex);
        }
    }

    public static AttachmentServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean deleteById(Long id) throws ServiceException {
        return false;
    }

    @Override
    public void deleteByBookId(Long bookId) throws ServiceException {

        try {
            List<Long> allByBookId = attachmentDao.findAllAttachmentIdByBookId(bookId);

            for (Long attachmentId : allByBookId) {
                attachmentDao.deleteById(attachmentId);
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> update) throws ServiceException {
        return false;
    }

    @Override
    public List<AttachmentDto> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<AttachmentDto> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean add(Map<String, String> entity) throws ServiceException {
        return false;
    }

    public Long addFile(Part part) throws ServiceException {

        try (InputStream inputStream = part.getInputStream()) {
            String submittedFileName = part.getSubmittedFileName();

            Attachment attachment = new Attachment();
            attachment.setContentType(part.getContentType());
            attachment.setAbsoluteName(part.getSubmittedFileName());
            attachment.setExtension(submittedFileName.substring(submittedFileName.lastIndexOf(".")));
            attachment.setFileSize(part.getSize());
            attachment.setUploadPath(UPLOAD_DIRECTORY.substring(UPLOAD_DIRECTORY.indexOf(ATTACHMENT_UPLOAD_DIRECTORY)));

            Path imagePath = new File(UPLOAD_DIRECTORY + submittedFileName).toPath();

            Files.copy(
                    inputStream,
                    imagePath,
                    StandardCopyOption.REPLACE_EXISTING
            );


            return attachmentDao.save(attachment);
        } catch (IOException | DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

    }

    public void attachFileToBook(Long bookId, Long attachmentId, boolean isToUpdate) throws ServiceException {
        try {
            if (isToUpdate) {
                attachmentDao.deleteAttachedFile(bookId, attachmentId);
            }
            attachmentDao.attachFileToBook(bookId, attachmentId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AttachmentDto> findAllByBookId(Long bookId) throws ServiceException {
        try {
            return attachmentDao.findAllByBookId(bookId);
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

}
