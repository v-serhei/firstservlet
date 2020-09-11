package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.util.ConnectionPoolImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/pool")
public class ConnectionPoolInitializer extends HttpServlet {
    @Override
    public void init() {
        ConnectionPoolImpl.getInstance().initConnectionPool();
    }
}
