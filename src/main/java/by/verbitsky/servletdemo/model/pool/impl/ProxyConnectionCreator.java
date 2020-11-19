package by.verbitsky.servletdemo.model.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * The enum Proxy connection creator. Provides proxy connection creating
 * Uses {@link PoolPropertyManager} to get properties of data base connection
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see PoolPropertyManager
 * @see ProxyConnection
 */
enum ProxyConnectionCreator {
    INSTANCE;
    private final Logger logger = LogManager.getLogger();
    private static final String PROPERTY_DB_URL = "db.url";
    private static final String PROPERTY_DB_DRIVER = "db.driver";
    private boolean isInitialized;
    private Properties properties;

    /**
     * Init creator. requests for properties to create connection to data base.
     * Register Sql driver
     *
     * @throws PoolException the pool exception
     */
    public void initCreator() throws PoolException {
        logger.log(Level.INFO, "init proxy connection creator");
        properties = PoolPropertyManager.INSTANCE.getAllProperties();
        registerDBDriver();
        isInitialized = true;
    }

    /**
     * Receives proxy connection from DriverManager
     *
     * @return the proxy connection
     * @throws PoolException if connection get failed
     * @throws RuntimeException if creator wasn't initialized
     */
    public ProxyConnection createConnection() throws PoolException {
        Connection realConnection;
        if (isInitialized) {
            String urlPath = properties.getProperty(PROPERTY_DB_URL);
            try {
                realConnection = DriverManager.getConnection(urlPath, properties);
            } catch (SQLException ex) {
                logger.log(Level.WARN, "ProxyConnectionCreator: Can't create connection", ex);
                throw new PoolException("ProxyConnectionCreator: Can't create connection", ex);
            }
        } else {
            logger.log(Level.FATAL, "ProxyConnectionCreator is not initialized");
            throw new RuntimeException("ProxyConnectionCreator is not initialized");
        }
        return new ProxyConnection(realConnection);
    }

    /**
     * Load data base driver Class
     */
    private void registerDBDriver() throws PoolException {
        String driver = properties.getProperty(PROPERTY_DB_DRIVER);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "ProxyConnectionCreator initialization: loading db driver class failed", e);
            throw new PoolException("ProxyConnectionCreator initialization error", e);
        }
    }

    /**
     * Deregister db driver.
     *
     * @throws PoolException the pool exception
     */
    public void deregisterDBDriver() throws PoolException {
        if (isInitialized) {
            try {
                Driver currentSQLDriver = DriverManager.getDriver(properties.getProperty(PROPERTY_DB_DRIVER));
                DriverManager.deregisterDriver(currentSQLDriver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "ProxyConnectionCreator: error deregister DB driver", e);
                throw new PoolException("ProxyConnectionCreator: error deregister DB driver", e);
            }
        } else {
            throw new PoolException("ProxyConnectionCreator deregisterDBDriver: Creator is not initialized");
        }
    }
}