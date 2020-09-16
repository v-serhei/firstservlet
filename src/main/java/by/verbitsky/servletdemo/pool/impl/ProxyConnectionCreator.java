package by.verbitsky.servletdemo.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.DBConnectionCreator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum ProxyConnectionCreator implements DBConnectionCreator<ProxyConnection> {
    INSTANCE;
    private boolean isPropertyLoad = false;
    private Properties properties;
    private final Logger logger = LogManager.getLogger();
    private static final String PROPERTY_FILE = "db/dbconnection.properties";
    private static final String PROPERTY_DB_DRIVER = "db.driver";
    private static final String PROPERTY_DB_URL = "db.url";


    private void initProxyConnectionCreator() {
        if (!isPropertyLoad) {
        properties = new Properties();
            try (InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
                properties.load(propertyFileInputStream);
                registerSQLDriver();
            } catch (IOException | ClassNotFoundException e) {
                logger.log(Level.ERROR, "Error to load connection properties", e);
                throw new RuntimeException("Error to load connection properties", e);
            }
            isPropertyLoad = true;
        }
    }

    private void registerSQLDriver() throws ClassNotFoundException {
        Class.forName(properties.getProperty(PROPERTY_DB_DRIVER));
    }

    @Override
    public ProxyConnection createConnection() throws PoolException {
        initProxyConnectionCreator();
        Connection realConnection;
        try {
            realConnection = DriverManager.getConnection(properties.getProperty(PROPERTY_DB_URL), properties);
        } catch (SQLException ex) {
             logger.log(Level.ERROR, "Can't create connection" , ex);
             throw new PoolException("Can't create connection" , ex);
        }
        return new ProxyConnection(realConnection);
    }

    @Override
    public void deregisterDBDriver() throws PoolException {
        try {
            Driver currentSQLDriver = DriverManager.getDriver(properties.getProperty(PROPERTY_DB_DRIVER));
            DriverManager.deregisterDriver(currentSQLDriver);
        } catch (SQLException e) {
            throw new PoolException("Error to load connection properties", e);
        }
    }
}