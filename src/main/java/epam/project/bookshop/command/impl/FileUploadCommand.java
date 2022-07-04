package epam.project.bookshop.command.impl;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class FileUploadCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIRECTORY = "C:/Users/User/Desktop/bookshop/src/main/webapp/pages/img/uploads";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String uploadPath = UPLOAD_DIRECTORY + File.separator;
//        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(UPLOAD_DIRECTORY + File.separator);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                logger.info("file name: " + fileName);
                part.write(uploadPath + File.separator + fileName);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return "pages/admin/file_upload.jsp";
    }
}
//        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
//        logger.info("uploadPath: " + uploadPath);
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        try {
//            for (Part part : request.getParts()) {
//                String fileName = part.getSubmittedFileName();
//                logger.info("fileName: " + fileName);
//                part.write(uploadPath + File.separator + fileName);
//            }
//        } catch (IOException | ServletException e) {
//            e.printStackTrace();
//        }
//        return "file_upload.jsp";
//    }

//        try {
//
//            Part part = request.getPart("file");
//
//            try (InputStream inputStream = part.getInputStream()) {
//                String submittedFileName = part.getSubmittedFileName();
//                Path imagePath = new File(UPLOAD_DIRECTORY + submittedFileName).toPath();
//
//                long bytes = Files.copy(
//                        inputStream,
//                        imagePath,
//                        StandardCopyOption.COPY_ATTRIBUTES
//                );
//            }
//        } catch (IOException | ServletException e) {
//            throw new CommandException(e);
//        }
//        return "index.jsp";

//String applicationRoot = request.getServletContext().getRealPath("");
//        logger.info("applicationRoot: "+ applicationRoot);
//        String uploadFileDir = applicationRoot + UPLOAD_DIRECTORY + File.separator;
//        logger.info("uploadFileDir: " + uploadFileDir);
//
//        File fileSaveDir = new File(uploadFileDir);
//        if (!fileSaveDir.exists()) {
//            fileSaveDir.mkdirs();
//        }
//
//        logger.info("Upload File Directory: " + fileSaveDir.getAbsolutePath());
//
//        AtomicBoolean nameNeedDetecting = new AtomicBoolean(true);
//        StringBuilder randFileName = new StringBuilder();
//        try {
//            request.getParts()
//                    .forEach(
//                            part -> {
//                                try {
//                                    if (nameNeedDetecting.compareAndSet(true, false)) {
//                                        String name = part.getSubmittedFileName();
//                                        logger.info("submitted fila name: " + name);
//                                        randFileName.append(UUID.randomUUID() + name.substring(name.lastIndexOf(".")));
//                                    }
//                                    part.write(uploadFileDir + randFileName);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                    );
//        } catch (IOException | ServletException e) {
//            e.printStackTrace();
//        }
//
//        return "index.jsp";
//


//     String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        try {
//            for (Part part : request.getParts()) {
//                String fileName = part.getSubmittedFileName();
//                part.write(uploadPath + File.separator + fileName);
//            }
//        } catch (IOException | ServletException e) {
//            e.printStackTrace();
//        }
//
//        return "file_upload";
//    }
