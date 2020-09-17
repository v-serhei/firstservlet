package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.pool.impl.ConnectionPool;
import by.verbitsky.servletdemo.pool.impl.ProxyConnectionCreator;
import by.verbitsky.servletdemo.service.SessionService;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MainServlet",
            urlPatterns = {"", "/index.jsp", "/controller", "/login", "/logout","/main", "/registration"})

public class MainServlet extends HttpServlet {
    private final SessionService sessionService = UserService.INSTANCE;
    private static final String FORM_ACTION = "param.jsp.commandtype";
    private static final String RESULT_PAGE = "attr.result.page";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get session or create new
        HttpSession session = request.getSession(true);
        sessionService.processNewSession(session);
        String commandParameter = WebResourcesManager.getInstance().getProperty(FORM_ACTION);
        //get String command type from request
        String cmd = request.getParameter(commandParameter);
        //create S-R content
        SessionRequestContent content = new SessionRequestContent(request);
        //Create command
        Command command = CommandProvider.defineCommand(cmd);
        if (command == null) {
            //todo redirect to error page with error message
        }
        //Process command
        command.execute(content);
        //update request and session attributes
        content.pushAttributesToRequest(request);
        content.pushAttributesToSession(request);
        //get result page
        String page = (String) request.getAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE));
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserService.INSTANCE.processNewSession(session);
        String reqPage = request.getRequestURI();
        String resultPage = WebResourcesManager.getInstance().getProperty(reqPage);
        request.getRequestDispatcher(resultPage).forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance().initConnectionPool(ProxyConnectionCreator.INSTANCE);
        super.init();
    }
}
