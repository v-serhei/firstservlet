package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.PageParameterNames;
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
                "/mainpage",
                "/logout",
                "/register",
                "/langswitch",
                "/profile",
                "/admin"
})

public class MainServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processUserRequest(request, response);
    }

    private void processUserRequest (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //Command lastCommand = (Command) request.getSession(false).getAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND);
        String cmd = request.getParameter(PageParameterNames.ACTION);
        SessionRequestContent content = new SessionRequestContent(request, response);
        Command command = CommandProvider.defineCommand(cmd);
        CommandResult result = command.execute(content);
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, command);
        if (result.isRedirect()) {
            response.sendRedirect(result.getResultPage());
        } else {
            request.getRequestDispatcher(result.getResultPage()).forward(request, response);
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
todo обработка ошибок и страницы с ошибками
todo дописать префикс к редиректу
todo last command - добавить атт и обрабатывать
todo смена языка
todo - заменить кнопки на input"ы с соответствующими названиями и параметрами
todo добавить меню на страницы логина и регистрации (кнопка home или другая навигация) + навигация на домашнюю страницу


 */