//package epam.project.bookshop.controller;
//
//import epam.project.bookshop.command.Command;
//import epam.project.bookshop.command.CommandType;
//import epam.project.bookshop.exception.CommandException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.StandardCopyOption;
//
//@WebServlet(value = {"/fileUpload", "*.do"})
//@MultipartConfig(fileSizeThreshold = 1024 * 1024,
//        maxFileSize = 1024 * 1024 * 5,
//        maxRequestSize = 1024 * 1024 * 5)
//public class FileUpload extends HttpServlet {
//
//    private static final Logger logger = LogManager.getLogger();
//    private static final String UPLOAD_DIRECTORY = "C:/Users/User/Desktop/bookshop/src/main/webapp/pages/img/uploads";
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        controllerCommand(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//
//        try {
//
//            Part part = req.getPart("file");
//
//            try (InputStream inputStream = part.getInputStream()) {
//                String submittedFileName = part.getSubmittedFileName();
//                Path imagePath = new File(UPLOAD_DIRECTORY  + File.separator + submittedFileName).toPath();
//
//                long bytes = Files.copy(
//                        inputStream,
//                        imagePath,
//                        StandardCopyOption.REPLACE_EXISTING
//                );
//            }
//        } catch (IOException | ServletException e) {
//            throw new ServletException(e);
//        }
//        req.getRequestDispatcher("pages/admin/file_upload.jsp").forward(req, resp);
////        controllerCommand(req, resp);
//    }
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//    }
//
//    private void controllerCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//
//        String command = req.getParameter("command");// input dagi name
//        Command execute = CommandType.castToCommand(command);
//
//        String page;
//        try {
//            page = execute.execute(req);
//
//            req.getRequestDispatcher(page).forward(req, resp);
//
//        } catch (CommandException e) {
//            throw new ServletException(e); // 2
//        }
//    }
//}