package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.common.EmptyCommand;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Front controller servlet. Provides processing users requests
 * It defines user commands and provides routing to result page
 * <p>
 * If command couldn't be defined - controller redirects user to error page with 404 error code
 * <p>
 * If controller receives an exception while command executing - it redirects user to error page with 500 error code
 *
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see Command
 * @see CommandResult
 * @see CommandType
 * @see HttpServlet
 */

@WebServlet(name = "FrontController", urlPatterns = "/do/*")
@SuppressWarnings("serial")
public class FrontController extends HttpServlet {
    private final Logger logger = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    private void processUserRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Command command = CommandProvider.defineCommand(request.getRequestURI());
        if (command instanceof EmptyCommand) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            CommandResult result;
            try {
                SessionRequestContent content = new SessionRequestContent(request);
                result = command.execute(content);
                //if session was not invalidated
                if (request.getSession(false) != null) {
                    content.pushAttributesToSession();
                }
                content.pushAttributesToRequest();
                if (result.isRedirect()) {
                    response.sendRedirect(result.getResultPage());
                } else {
                    request.getRequestDispatcher(result.getResultPage()).forward(request, response);
                }
            } catch (CommandException e) {
                logger.log(Level.WARN, generateLogMessage(e));
                request.setAttribute(AttributeName.REQUESTED_URL, request.getRequestURL());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private String generateLogMessage(CommandException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        sb.append(", cause: ");
        sb.append(e.getCause().getClass().getSimpleName());
        sb.append(" :");
        sb.append(e.getCause().getLocalizedMessage());
        return sb.toString();
    }

    /**
     * Initiate Connections pool, which provides connections for data base
     *
     * @see ConnectionPoolImpl
     */

    @Override
    public void init() throws ServletException {
        ConnectionPoolImpl.getInstance().initConnectionPool();
        super.init();
    }
}
