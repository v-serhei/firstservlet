package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.PagePath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Download servlet. Provides file downloading for application users
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
@WebServlet(name = "DownloadServlet", urlPatterns = "/process/download")

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {
    private static final String MIME_TYPE_ZIP = "application/zip";
    private final Logger logger = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = (String) request.getAttribute(AttributeName.DOWNLOAD_FILE_PATH);
        if (fileName == null || fileName.isEmpty()) {
            logger.log(Level.ERROR, "DownloadServlet: error while send zip to client: received null file name");
            request.getRequestDispatcher(PagePath.FORWARD_SERVLET_DOWNLOAD_ERROR).forward(request, response);
        }
        File file = new File(fileName);
        response.setContentType(MIME_TYPE_ZIP);
        response.setHeader("Content-Disposition", "attachment; filename=\"" +  file.getName() + "\"");
        response.setContentLength((int) file.length());
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ServletOutputStream outputStream = response.getOutputStream()
        ) {
            int readBytes;
            while ((readBytes = inputStream.read()) != -1) {
                outputStream.write(readBytes);
            }
            outputStream.flush();
        }catch (IOException exception) {
            logger.log(Level.ERROR, "DownloadServlet: error while send zip to client", exception);
            request.getRequestDispatcher(PagePath.FORWARD_SERVLET_DOWNLOAD_ERROR).forward(request, response);
        }
    }
}
