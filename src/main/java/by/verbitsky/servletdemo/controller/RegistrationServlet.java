package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final String REGISTRATION_PAGE_PASS = "pages.jsp.registration";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mainJsp = WebResourcesManager.getInstance().getProperty(REGISTRATION_PAGE_PASS);
        //todo registration logic
        request.getRequestDispatcher(mainJsp).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mainJsp = WebResourcesManager.getInstance().getProperty(REGISTRATION_PAGE_PASS);
        //todo registration logic
        request.getRequestDispatcher(mainJsp).forward(request, response);
    }
}
