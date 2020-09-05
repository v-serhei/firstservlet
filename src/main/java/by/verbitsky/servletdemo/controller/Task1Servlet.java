package by.verbitsky.servletdemo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/controller")
public class Task1Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //объект request - мапа, где ключи - это name элементов на странице
        //a value - это значение атрибута value у этого элемента
        //<input type="hidden" name="time" value="${System.currentTimeMillis()}">
        //т.е. мы берем элемент с нэймом time и забираем значение - текущее время в милисеках

        String mSec = request.getParameter("time");
        float sec = ((float)(System.currentTimeMillis()-Long.parseLong(mSec)))/1000;
        request.setAttribute("result", sec);
        String button = request.getParameter("submit");
        request.setAttribute("button", button );
        request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
