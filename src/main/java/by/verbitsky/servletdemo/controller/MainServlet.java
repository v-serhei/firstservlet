package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.command.impl.EmptyCommand;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainServlet",
        urlPatterns = {
                "/login",
                "/logout",
                "/register",
                "/langswitch",
                "/mainpage",
                "/profile",
                "/admin"
        })

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
    private static final int PAGE_NOT_FOUND_STATUS_CODE = 404;
    private final Logger logger = LogManager.getLogger();
    private static final String REDIRECT_PAGE_PREFIX = "/audiobox";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    private void processUserRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cmd = request.getParameter(ParameterNames.ACTION);
        SessionRequestContent content = new SessionRequestContent(request, response);
        Command command = CommandProvider.defineCommand(cmd);
        if (command instanceof EmptyCommand) {
            response.sendError(PAGE_NOT_FOUND_STATUS_CODE);
        } else {
            CommandResult result;
            try {
                result = command.execute(content);
                //if session was not invalidated
                if (request.getSession(false) != null) {
                    content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, command);
                    content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_URI, result.getResultPage());
                    content.pushAttributesToSession(content.getRequest());
                }
                content.pushAttributesToRequest(content.getRequest());
                if (result.isRedirect()) {
                    response.sendRedirect(REDIRECT_PAGE_PREFIX.concat(result.getResultPage()));
                } else {
                    request.getRequestDispatcher(result.getResultPage()).forward(request, response);
                }
            } catch (CommandExecutionException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(e.getMessage());
                sb.append(", cause: ");
                sb.append(e.getCause().getLocalizedMessage());
                logger.log(Level.WARN, sb.toString());
                request.setAttribute(AttributesNames.REQUEST_ATTR_REQUESTED_URL, request.getRequestURL());
                request.getRequestDispatcher(ProjectPages.ERROR_PAGE).forward(request, response);
            }
        }
    }

    @Override
    public void init() throws ServletException {
        ConnectionPoolImpl.getInstance().initConnectionPool();
        super.init();
    }

    private String getStacktraceText(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element);
            sb.append("\n");
        }
        return sb.toString();
    }
}

/*


todo просмотреть все команды по порядку
todo пагинация
 валидация

todo обработка ошибок и страницы с ошибками
todo last command - добавить атт и обрабатывать
todo - заменить кнопки на input"ы с соответствующими названиями и параметрами


 */
