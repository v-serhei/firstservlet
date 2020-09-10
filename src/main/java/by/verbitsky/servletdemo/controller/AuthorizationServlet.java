package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.command.CommandProvider;
import by.verbitsky.servletdemo.command.Command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth-action")
public class AuthorizationServlet extends HttpServlet {
    private static final String FORM_ACTION_PARAMETER = "action";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmd = request.getParameter(FORM_ACTION_PARAMETER);
        Command command = CommandProvider.defineCommand(cmd);
        if (command == null) {
            response.sendRedirect("main");
        }
        String result = command.execute(request);
        request.getRequestDispatcher(result).forward(request, response);
    }
}
