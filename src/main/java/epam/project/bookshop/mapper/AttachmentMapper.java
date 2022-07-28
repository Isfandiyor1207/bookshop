package epam.project.bookshop.mapper;

import epam.project.bookshop.dto.AttachmentDto;
import epam.project.bookshop.entity.Attachment;
import epam.project.bookshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import static epam.project.bookshop.command.ParameterName.*;

public class AttachmentMapper implements BaseMapper<AttachmentDto> {

    private static final Logger logger = LogManager.getLogger();
    private static final AttachmentMapper instance = new AttachmentMapper();

    private AttachmentMapper(){}

    public static AttachmentMapper getInstance() {
        return instance;
    }

    @Override
    public AttachmentDto resultSetToDto(ResultSet resultSet) throws DaoException {
        AttachmentDto attachmentDto = new AttachmentDto();

        try {
            attachmentDto.setUploadPath(resultSet.getString(ATTACHMENT_UPLOAD_PATH));
            attachmentDto.setHashName(resultSet.getString(ATTACHMENT_HASH_NAME));
            attachmentDto.setAbsoluteName(resultSet.getString(ATTACHMENT_ABSOLUTE_NAME));
            attachmentDto.setExtension(resultSet.getString(ATTACHMENT_EXTENSION));
            return attachmentDto;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
}
