package by.verbitsky.servletdemo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    private static final String USER_NAME = "username";
    private static final String DEFAULT_USER_NAME = "guest";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /*
        String userName = (String) request.getAttribute(USER_NAME);
        if (userName.equals(DEFAULT_USER_NAME)) {
            doGet(request, response);
        }
        request.getRequestDispatcher("/pages/main.jsp").forward(request, response);
        //todo подменить ссылку через javascript

        */

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo посмотреть как сделать через session and cookies
       /*
        String errorVisible = (String) request.getAttribute("errorVisible");
        //обработать сессию
        getServletContext().getRequestDispatcher("/system_session_processor").include(request, response);

        if (errorVisible == null) {
            request.setAttribute("errorVisible", "none"); //лучше передать через атрибут сессии
        }
        request.setAttribute("username", "guest");
        request.setAttribute("authBlockVisible", "block"); //лучше передать через атрибут сессии
        request.setAttribute("logoutBlockVisible", "none");

        */
        request.getRequestDispatcher("/pages/main.jsp").forward(request, response);


    }
}