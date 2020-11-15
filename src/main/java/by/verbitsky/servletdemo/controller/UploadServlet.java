package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.controller.command.ParameterName;
import by.verbitsky.servletdemo.exception.AudioBoxException;
import by.verbitsky.servletdemo.util.FileUploadUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UploadServlet", urlPatterns = "/process/upload")
@MultipartConfig()
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 15;
    private final Logger logger = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long contentLengthLong = request.getContentLengthLong();
        String fullPath = "";
        boolean uploadResult = false;
        if (contentLengthLong < MAX_FILE_SIZE) {
            String singerName = request.getParameter(ParameterName.SINGER_ID);
            String albumTitle = request.getParameter(ParameterName.ALBUM_ID);
            Part part = request.getPart(ParameterName.PART_FILE_NAME);
            try {
                String fileName = part.getSubmittedFileName();
                Optional<String> uploadPath = FileUploadUtil.buildPathToSongFile(fileName, singerName, albumTitle);
                if (uploadPath.isPresent()) {
                    part.write(uploadPath.get());
                    fullPath = uploadPath.get();
                    uploadResult = true;
                }
            } catch (AudioBoxException e) {
                logger.log(Level.ERROR, "UploadServlet: error while uploading user file", e);
            }
        }
        if (uploadResult) {
            request.setAttribute(AttributeName.UPLOAD_FILE_PATH, fullPath);
            request.getRequestDispatcher(PagePath.FORWARD_SERVLET_CREATE_SONG).forward(request, response);
        } else {
            request.getRequestDispatcher(PagePath.FORWARD_SERVLET_UPLOAD_ERROR).forward(request, response);
        }
    }
}
