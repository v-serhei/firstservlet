package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
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
                //processing commands (post)
                "/login",
                "/logout",


                "/register",
                "/langswitch",

                //redirect commands (get)
                "/mainpage",
                "/profile",
                "/admin"
        })

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger();

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

        CommandResult result;
        try {
            result = command.execute(content);
            //if session was not invalidated
            if (request.getSession(false) != null) {
                content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, command);
                content.pushAttributesToSession(content.getRequest());
            }
            content.pushAttributesToRequest(content.getRequest());

            if (result.isRedirect()) {
                response.sendRedirect(result.getResultPage());
            } else {
                request.getRequestDispatcher(result.getResultPage()).forward(request, response);
            }
        } catch (CommandExecutionException e) {
            //todo redirect to error page with stacktrace
        }
    }

    @Override
    public void init() throws ServletException {
        ConnectionPoolImpl.getInstance().initConnectionPool();
        super.init();
    }


}
/*


todo просмотреть все команды по порядку, доделать регистрацию
todo пагинация
 доделать логаут
 валидация
 страница ошибок













todo обработка ошибок и страницы с ошибками
todo дописать префикс к редиректу (убрать дублирующие ссылки в константах)
todo last command - добавить атт и обрабатывать
todo смена языка
todo - заменить кнопки на input"ы с соответствующими названиями и параметрами


 */
