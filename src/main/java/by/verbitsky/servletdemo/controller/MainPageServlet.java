package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    private static final String MAIN_PAGE_URL = "pages.jsp.main";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService.INSTANCE.processNewSession(request.getSession(true));
        String page = WebResourcesManager.getInstance().getProperty(MAIN_PAGE_URL);
        request.getRequestDispatcher(page).forward(request, response);
    }
}