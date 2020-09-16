package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.dao.UserDAO;
import by.verbitsky.servletdemo.entity.WebUser;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPool;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.service.AuthorizationService;
import by.verbitsky.servletdemo.service.SessionService;
import by.verbitsky.servletdemo.util.WebResourcesManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum UserService implements AuthorizationService, SessionService {
    INSTANCE;
    private static final String DEFAULT_LOGIN_RESULT = "false";
    private static final String TRUE_LOGIN_RESULT = "true";
    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    private static final String ATTR_SESSION_USER = "attr.session.user";
    private static final String ATTR_SESSION_LOGIN_RESULT = "attr.session.loginresult";
    private static final String DEFAULT_USER_NAME = "guest";
    private static final String DEFAULT_USER_EMAIL = "default";
    private static final String LOGIN_BLOCK = "attr.loginblockdisplay";
    private static final String LOGOUT_BLOCK = "attr.logoutblockdisplay";
    private static final String USER_GREETING = "attr.usergreeting";
    private static final String DISPLAY_VALUE_TRUE = "block";
    private static final String DISPLAY_VALUE_FALSE = "none";
    private static final String HELLO_MESSAGE = "Hello, ";

    private final Logger logger = LogManager.getLogger();
    private static ConnectionPool pool = ConnectionPool.getInstance();
    private static UserDAO userDAO = new UserDAO();

    @Override
    public boolean checkLogin(String username, String password) {
        if (username.isEmpty() || username == null || password.isEmpty() || password == null) {
            return false;
        }
        ProxyConnection connection;
        WebUser user = null;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            user = userDAO.getUserByName(username);
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo создать результат перенаправления на страницу ошибки
            e.printStackTrace();
        }
        boolean result = false;
        if (user != null) {
            String userPassword = getHashedPassword(password);
            String dbPassword = user.getUserPassword();
            if (userPassword.equals(dbPassword)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void logout(HttpSession userSession) {
        userSession.invalidate();
    }

    @Override
    public boolean validateUserEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    @Override
    public boolean existUserEmail(String email) {
        ProxyConnection connection;
        boolean result = true;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.existUserEmail(email.toLowerCase());
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo создать результат перенаправления на страницу ошибки
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public boolean existUserName(String userName) {
        ProxyConnection connection;
        boolean result = true;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.existUserName(userName.toLowerCase());
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addRegisteredUser(WebUser user, String password) {
        ProxyConnection connection;
        String hashedPassword = getHashedPassword(password);
        boolean result = false;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.addNewUser(user, hashedPassword);
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo log
        }
        return result;
    }

    @Override
    public void processNewSession(HttpSession session) {
        if (session != null) {
            if (session.isNew()) {
                session.setMaxInactiveInterval(DEFAULT_SESSION_LIVE_TIME);
                String attrName = WebResourcesManager.getInstance().getProperty(ATTR_SESSION_USER);
                WebUser user = new WebUser(session, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL);
                session.setAttribute(attrName, user);
                attrName = WebResourcesManager.getInstance().getProperty(ATTR_SESSION_LOGIN_RESULT);
                session.setAttribute(attrName, DEFAULT_LOGIN_RESULT);
                attrName = WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_TRUE);
                attrName = WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_FALSE);
                attrName = WebResourcesManager.getInstance().getProperty(USER_GREETING);
                session.setAttribute(attrName, HELLO_MESSAGE.concat(user.getUserName()));
            }
        }
    }

    @Override
    public void updateLoginAttributes(HttpSession session, String userName) {
        if (session != null && userName != null) {
            String attrName = WebResourcesManager.getInstance().getProperty(ATTR_SESSION_USER);
            WebUser user = (WebUser) session.getAttribute(attrName);
            user.setUserName(userName);
            attrName = WebResourcesManager.getInstance().getProperty(ATTR_SESSION_LOGIN_RESULT);
            session.setAttribute(attrName, TRUE_LOGIN_RESULT);
            attrName = WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK);
            session.setAttribute(attrName, DISPLAY_VALUE_FALSE);
            attrName = WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK);
            session.setAttribute(attrName, DISPLAY_VALUE_TRUE);
            attrName = WebResourcesManager.getInstance().getProperty(USER_GREETING);
            session.setAttribute(attrName, HELLO_MESSAGE.concat(user.getUserName()));
        }
    }

    private String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }
}
