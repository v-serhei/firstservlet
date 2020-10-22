package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandProvider;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.impl.ready.EmptyCommand;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.ParameterNames;
import by.verbitsky.servletdemo.controller.command.PagePaths;
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
                    content.addSessionAttribute(AttributeNames.SESSION_ATTR_LAST_COMMAND, command);
                    content.addSessionAttribute(AttributeNames.SESSION_ATTR_LAST_URI, result.getResultPage());
                    content.pushAttributesToSession();
                }
                content.pushAttributesToRequest();
                if (result.isRedirect()) {
                    response.sendRedirect(REDIRECT_PAGE_PREFIX.concat(result.getResultPage()));
                } else {
                    request.getRequestDispatcher(result.getResultPage()).forward(request, response);
                }
            } catch (CommandExecutionException e) {
                logger.log(Level.WARN, generateLogMessage(e));
                request.setAttribute(AttributeNames.REQUEST_ATTR_REQUESTED_URL, request.getRequestURL());
                request.getRequestDispatcher(PagePaths.ERROR_PAGE).forward(request, response);
            }
        }
    }

    private String generateLogMessage(CommandExecutionException e) {
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
