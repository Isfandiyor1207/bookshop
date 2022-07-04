package epam.project.bookshop.controller;

import epam.project.bookshop.command.Command;
import epam.project.bookshop.command.CommandType;
import epam.project.bookshop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@WebServlet(value = {"/fileUpload", "*.do"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5)
public class FileUpload extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();
    private static final String UPLOAD_DIRECTORY = "C:/Users/User/Desktop/bookshop/src/main/webapp/pages/img/uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controllerCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String applicationRoot = req.getServletContext().getRealPath("");
        logger.info("applicationRoot: " + applicationRoot);
        String uploadFileDir = applicationRoot + File.separator + "upload" + File.separator;
        logger.info("uploadFileDir: " + uploadFileDir);

        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        logger.info("Upload File Directory: " + fileSaveDir.getAbsolutePath());

        AtomicBoolean nameNeedDetecting = new AtomicBoolean(true);
        StringBuilder randFileName = new StringBuilder();
        try {
            req.getParts()
                    .forEach(
                            part -> {
                                try {
                                    if (nameNeedDetecting.compareAndSet(true, false)) {
                                        String name = part.getSubmittedFileName();
                                        logger.info("submitted fila name: " + name);
                                        randFileName.append(UUID.randomUUID() + name.substring(name.lastIndexOf(".")));
                                    }
                                    part.write(uploadFileDir + randFileName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("pages/admin/file_upload.jsp").forward(req, resp);
//        controllerCommand(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    private void controllerCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String command = req.getParameter("command");// input dagi name
        Command execute = CommandType.castToCommand(command);

        String page;
        try {
            page = execute.execute(req);

            req.getRequestDispatcher(page).forward(req, resp);

        } catch (CommandException e) {
            throw new ServletException(e); // 2
        }
    }
}