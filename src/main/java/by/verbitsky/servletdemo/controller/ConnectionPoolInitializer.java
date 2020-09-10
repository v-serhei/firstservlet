package by.verbitsky.servletdemo.controller;

import by.verbitsky.servletdemo.util.ConnectionPoolImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/pool")
public class ConnectionPoolInitializer extends HttpServlet {
    private static final String DB_PROPERTIES_FILE = "db/dbconnection.properties";

    @Override
    public void init() {
        ConnectionPoolImpl pool = ConnectionPoolImpl.getInstance();
        InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
        try {
            pool.initConnectionPool(propertyFileInputStream);
        } catch (SQLException throwables) {
            //todo загушить с error page
            throwables.printStackTrace();
        }
    }
}
