package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.pool.impl.ConnectionPool;
import by.verbitsky.servletdemo.pool.impl.ProxyConnectionCreator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet ("/systemServlet_poolInitServlet")
public class ConnectionPoolInitializer extends HttpServlet {
    @Override
    public void init() {
        //ConnectionPooL.getInstance().initConnectionPool();
        ConnectionPool.getInstance().initConnectionPool(ProxyConnectionCreator.INSTANCE);
    }
}
