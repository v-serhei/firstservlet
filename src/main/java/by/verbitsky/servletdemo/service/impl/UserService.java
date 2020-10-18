package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.dao.UserDao;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Locale;

public enum UserService {
    INSTANCE;
    private final ConnectionPoolImpl pool = ConnectionPoolImpl.getInstance();

    public void processLanguageSwitch(SessionRequestContent content) {
        String lang = content.getRequestParameter(ParameterNames.ACTION);
        Locale locale;
        switch (lang.toLowerCase()) {
            case "en": {
                locale = new Locale("en", "US");
                break;
            }
            case "ru": {
                locale = new Locale("ru", "RU");
                break;
            }
            default:
                locale = Locale.getDefault();
        }
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LOCALE, locale);
        //String resultPage = resourcesManager.getProperty(MAIN_PAGE);
        //content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        content.pushAttributesToRequest(content.getRequest());
        content.pushAttributesToSession(content.getRequest());
    }



    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public User findUserByEmail(String email) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findByEmail(email.toLowerCase());
        pool.releaseConnection(connection);
        return user;
    }

    public User findUserByName(String userName) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findUserByName(userName.toLowerCase());
        pool.releaseConnection(connection);
        return user;
    }

    public boolean addRegisteredUser(User user) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        boolean result = userDAO.create(user);
        pool.releaseConnection(connection);
        return result;
    }
}
