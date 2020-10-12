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
                "",
               // "/index.jsp",
                "/mainpage",
                "/login",
                "/logout",
                "/register",
                "/langswitch",
                "/profile",
                "/admin"
})

public class MainServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger();
    private static final String URL_REDIRECT_PREFIX = "/firstservlet";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command last = (Command) request.getSession(false).getAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND);
        System.out.println(last);

        String cmd = request.getParameter(PageParameterNames.ACTION);
        //create S-R content
        SessionRequestContent content = new SessionRequestContent(request, response);
        //Create command
        Command command = CommandProvider.defineCommand(cmd);
        System.out.println(command);

        CommandResult result = command.execute(content);
        if (result.isRedirect()) {
            System.out.println("do post redirect to page: " + URL_REDIRECT_PREFIX.concat(result.getResultPage()));
            response.sendRedirect(URL_REDIRECT_PREFIX.concat(result.getResultPage()));
        } else {
            System.out.println("do post forward to page: " + (result.getResultPage()));
            request.getRequestDispatcher(result.getResultPage()).forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command last = (Command) request.getSession(false).getAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND);
        System.out.println(last);

        String cmd = request.getParameter(PageParameterNames.ACTION);
        //create S-R content
        SessionRequestContent content = new SessionRequestContent(request, response);
        //Create command
        Command command = CommandProvider.defineCommand(cmd);
        System.out.println(command);

        CommandResult result = command.execute(content);
        if (result.isRedirect()) {
            System.out.println("do post redirect to page: " + URL_REDIRECT_PREFIX.concat(result.getResultPage()));
            response.sendRedirect(URL_REDIRECT_PREFIX.concat(result.getResultPage()));
        } else {
            System.out.println("do post forward to page: " + (result.getResultPage()));
            request.getRequestDispatcher(result.getResultPage()).forward(request, response);
        }


        /*
        //todo подумать как редиректить красиво
        String reqPage = request.getRequestURI();
        String resultPage = WebResourcesManager.getInstance().getProperty(reqPage);
        System.out.println("doget forward to page: "+resultPage);
        request.getRequestDispatcher(resultPage).forward(request, response);
*/
    }

    @Override
    public void init() throws ServletException {
        ConnectionPoolImpl.getInstance().initConnectionPool();
        super.init();
    }
}