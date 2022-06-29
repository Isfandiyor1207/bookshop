package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.taglibs.standard.lang.jstl.Constants;

import java.io.File;
import java.io.IOException;

public class FileUploadCommand implements Command {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                part.write(uploadPath + File.separator + fileName);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return "file_upload";
    }

//    private String getFileName(Part part) {
//        for (String content : part.getHeader("content-disposition").split(";")) {
//            if (content.trim().startsWith("filename"))
//                return content.substring(content.indexOf("=") + 2, content.length() - 1);
//        }
//        return Constants.DEFAULT_FILENAME;
//    }
}
