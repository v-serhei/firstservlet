package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final String FORM_ACTION = "param.jsp.commandtype";
    private static final String RESULT_PAGE = "attr.result.page";
    private static final String LOGIN_PAGE_PATH = "pages.jsp.login";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService.INSTANCE.processNewSession(request.getSession(true));
        String commandParameter = WebResourcesManager.getInstance().getProperty(FORM_ACTION);
        String cmd = request.getParameter(commandParameter);
        Command command = CommandProvider.defineCommand(cmd);
        /*
        if (command == null) {
            //todo redirect to error page
        }*/
        CommandResult result = command.execute(request);
        String resultPageParam = WebResourcesManager.getInstance().getProperty(RESULT_PAGE);
        addRequestParams(result, request);
        String page = result.getAttribute(resultPageParam);
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService.INSTANCE.processNewSession(request.getSession(true));
        String page = WebResourcesManager.getInstance().getProperty(LOGIN_PAGE_PATH);
        request.getRequestDispatcher(page).forward(request, response);
    }

    private void addRequestParams (CommandResult result, HttpServletRequest request) {
        Set<Map.Entry<String, String>> resultSet = result.getResults().entrySet();
        for (Map.Entry<String, String> element : resultSet) {
            request.setAttribute(element.getKey(), element.getValue());
        }
    }
}
