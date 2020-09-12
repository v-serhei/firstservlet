package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.util.ProjectManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class AuthorizationServlet extends HttpServlet {
    private static final String FORM_ACTION_PARAMETER_NAME = "action";
    private static final String LOGIN_PAGE_PATH = "pages.jsp.login";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        String cmd = request.getParameter(FORM_ACTION_PARAMETER_NAME);
        Command command = CommandProvider.defineCommand(cmd);
        if (command == null) {
            response.sendRedirect("main");
        }
        String result = command.execute(request);
        request.getRequestDispatcher(result).forward(request, response);*/

        String nextpage = ProjectManager.getInstance().getProperty(LOGIN_PAGE_PATH);
        //todo registration logic
        request.getRequestDispatcher(nextpage).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextpage = ProjectManager.getInstance().getProperty(LOGIN_PAGE_PATH);
        //todo registration logic
        request.getRequestDispatcher(nextpage).forward(request, response);
    }
}
