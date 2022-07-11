package epam.project.bookshop.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class AttachmentDto extends GenericDto{

    private String absoluteName;

    private String hashName;

    private String uploadPath;

    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAbsoluteName() {
        return absoluteName;
    }

    public void setAbsoluteName(String absoluteName) {
        this.absoluteName = absoluteName;
    }

    public String getHashName() {
        return hashName;
    }

    public void setHashName(String hashName) {
        this.hashName = hashName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentDto that = (AttachmentDto) o;
        return Objects.equals(absoluteName, that.absoluteName) && Objects.equals(hashName, that.hashName) && Objects.equals(uploadPath, that.uploadPath) && Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absoluteName, hashName, uploadPath, extension);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AttachmentDto.class.getSimpleName() + "[", "]")
                .add("absoluteName='" + absoluteName + "'")
                .add("hashName='" + hashName + "'")
                .add("uploadPath='" + uploadPath + "'")
                .add("extension='" + extension + "'")
                .toString();
    }
}
