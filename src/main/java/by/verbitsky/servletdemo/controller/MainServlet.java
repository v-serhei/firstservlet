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

@WebServlet(name = "MainServlet",
        urlPatterns = {
                "/login",
                "/review",
                "/logout",
                "/register",
                "/langswitch",
                "/mainpage",
                "/profile",
                "/admin",
                "/compilation",
                "/orderPage"
        })
@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
    private static final String URL_PARAMETER_PREFIX = "?";
    private static final String REDIRECT_PAGE_PREFIX = "/audiobox";
    private static final int PAGE_NOT_FOUND_STATUS_CODE = 404;
    private static final int SERVER_ERROR_CODE = 500;
    private final Logger logger = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    private void processUserRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cmd = request.getParameter(ParameterName.ACTION);
        SessionRequestContent content = new SessionRequestContent(request);
        Command command = CommandProvider.defineCommand(cmd);
        if (command instanceof EmptyCommand) {
            response.sendError(PAGE_NOT_FOUND_STATUS_CODE);
        } else {
            CommandResult result;
            try {
                result = command.execute(content);
                //if session was not invalidated
                if (request.getSession(false) != null) {
                    content.addSessionAttribute(AttributeName.SESSION_LAST_COMMAND, command);
                    content.addSessionAttribute(AttributeName.SESSION_USER_LAST_QUERY, constructQuery(request));
                    content.pushAttributesToSession();
                }
                content.pushAttributesToRequest();
                int i = 1;
                if (result.isRedirect()) {
                    response.sendRedirect(REDIRECT_PAGE_PREFIX.concat(result.getResultPage()));
                } else {
                    request.getRequestDispatcher(result.getResultPage()).forward(request, response);
                }
            } catch (CommandException e) {
                logger.log(Level.WARN, generateLogMessage(e));
                request.setAttribute(AttributeName.REQUESTED_URL, request.getRequestURL());
                response.sendError(SERVER_ERROR_CODE);
            }
        }
    }

    private String constructQuery(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceFirst(REDIRECT_PAGE_PREFIX,"");
        String query = request.getQueryString();
        StringBuilder sb = new StringBuilder(uri);
        if (query != null && !query.isEmpty()) {
            sb.append(URL_PARAMETER_PREFIX);
            sb.append(query);
        }
        return sb.toString();
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

    @Override
    public void init() throws ServletException {
        ConnectionPoolImpl.getInstance().initConnectionPool();
        super.init();
    }
}

/*


todo просмотреть все команды по порядку (посмотреть как обрабатываются ошибки - пробросить все наверх)
 доделать пагинацию на главной странице, проверить как работает
 валидация

todo last command - добавить атт и обрабатывать
todo - заменить кнопки на input"ы с соответствующими названиями и параметрами
 посмотреть где пропущена интернационализация на страницах (стр ошибок)

 */
