package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.exception.FileUtilException;
import com.sun.javafx.fxml.PropertyNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

enum UploadPropertyManager {
    INSTANCE;
    private static final String PROPERTY_FILE = "contentfiles/filerepository.properties";
    private final Logger logger = LogManager.getLogger();
    private Properties properties = new Properties();
    private boolean isInit = false;

    public void initManager() throws FileUtilException {
        if (!isInit) {
            logger.log(Level.INFO, "init Upload Property Manager");
            properties = new Properties();
            try (InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
                if (propertyFileInputStream == null) {
                    throw new FileUtilException("Error to load properties - property file not found");
                }
                properties.load(propertyFileInputStream);
            } catch (IOException e) {
                throw new FileUtilException("Error to load properties", e);
            }
            isInit = true;
        }
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
}