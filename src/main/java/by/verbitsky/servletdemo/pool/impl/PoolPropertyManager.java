package by.verbitsky.servletdemo.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import com.sun.javafx.fxml.PropertyNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

enum PoolPropertyManager {
    INSTANCE;
    private static final String PROPERTY_FILE = "db/dbconnection.properties";
    private final Logger logger = LogManager.getLogger();
    private Properties properties;

    {
        properties = new Properties();
    }

    public void initManager() throws PoolException {
        logger.log(Level.INFO, "init pool property manager");
        properties = new Properties();
        try (InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            if (propertyFileInputStream == null) {
                logger.log(Level.FATAL, "Error to load properties - property file not found");
                throw new PoolException("Error to load properties - property file not found");
            }
            properties.load(propertyFileInputStream);
        } catch (IOException e) {
            logger.log(Level.FATAL, "Error to load connection properties", e);
            throw new PoolException("Error to load connection properties", e);
        }
    }

    public Optional<Integer> getIntProperty(String propertyName) {
        String extracted;
        Optional<Integer> property;
        try {
            extracted = properties.getProperty(propertyName);
        } catch (PropertyNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested property <");
            sb.append(propertyName);
            sb.append("> not found");
            logger.log(Level.WARN, sb.toString());
            property = Optional.empty();
            return property;
        }
        try {
            int value = Integer.parseInt(extracted);
            property = Optional.of(value);
        } catch (NumberFormatException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested property <");
            sb.append(propertyName);
            sb.append("> is not an Integer value");
            logger.log(Level.WARN, sb.toString());
            property = Optional.empty();
        }
        return property;
    }

    public Optional<String> getStringProperty(String propertyName) {
        Optional<String> property;
        String extracted = properties.getProperty(propertyName);
        try {
            property = Optional.of(extracted);
        } catch (PropertyNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested property <");
            sb.append(propertyName);
            sb.append("> not found");
            logger.log(Level.WARN, sb.toString());
            property = Optional.empty();
        }
        return property;
    }

    public Properties getAllProperties() {
        return properties;
    }
}