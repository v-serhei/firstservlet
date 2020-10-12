package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.dao.UserDao;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.service.WebResourcesManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public enum UserService {
    INSTANCE;
    //todo вынести в ресурсы
    //session and req attributes
    private static final String ATTR_SESSION_USER = "attr.session.user";
    private static final String ATTR_SESSION_LOCALE = "attr.session.locale";
    private static final String ATTR_REQUEST_LOGIN_RESULT = "attr.request.login.error";
    private static final String ATTR_REQUEST_REG_ERROR_MESSAGE = "attr.request.reg.error";
    private static final String ATTR_REQUEST_REG_RESULT = "attr.request.reg.result";
    private static final String LOGIN_PAGE = "pages.jsp.login";
    private static final String MAIN_PAGE = "pages.jsp.main";
    //request parameters
    private static final String PARAM_EMAIL = "param.user.email";
    private static final String PARAM_LANGUAGE = "param.user.lang";
    private static final String PARAM_USER_NAME = "param.user.name";
    private static final String PARAM_PASSWORD = "param.password";
    private static final String PARAM_PASSWORD_SECOND = "param.password.second";
    private static final String PARAM_FORM_ACTION = "param.jsp.commandtype";
    //other attributes
    private static final String REGISTER_ERROR_MESSAGE_DIFFERENT_PASSWORDS = "reg.error.message.different.passwords";
    private static final String REGISTER_ERROR_MESSAGE_EXIST_USER = "reg.error.message.exist.user";
    private static final String REGISTER_PAGE = "pages.jsp.registration";
    private static final String RESULT_PAGE = "attr.result.page";
    private static final String REGISTER_ERROR_MESSAGE_EXIST_EMAIL = "reg.error.message.exist.email";

    //private static final String REGISTER_ERROR_MESSAGE_WRONG_EMAIL = "Wrong user email";
    //private static final String REGISTER_ERROR_MESSAGE_WRONG_USER_NAME = "Wrong user name";
    //private static final String REGISTER_ERROR_MESSAGE_EMPTY_PASSWORD = "Empty password field";

    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    private static final int DEFAULT_USER_DISCOUNT = 0;
    private static final int DEFAULT_USER_ROLE_ID = 2;
    private static final int DEFAULT_BLOCKED_STATUS = 0;

    private final Logger logger = LogManager.getLogger();
    private final ConnectionPoolImpl pool = ConnectionPoolImpl.getInstance();
    private final WebResourcesManager resourcesManager = WebResourcesManager.getInstance();


    public void processLanguageSwitch(SessionRequestContent content) {
        String paramName = WebResourcesManager.getInstance().getProperty(PARAM_FORM_ACTION);
        String lang = content.getRequestParameter(paramName);
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
        content.addSessionAttribute(resourcesManager.getProperty(ATTR_SESSION_LOCALE), locale);
        String resultPage = resourcesManager.getProperty(MAIN_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        content.pushAttributesToRequest(content.getRequest());
        content.pushAttributesToSession(content.getRequest());
    }

    public void processRegistration(SessionRequestContent content) {
        String paramName = resourcesManager.getProperty(PARAM_USER_NAME);
        String userName = content.getRequestParameter(paramName);
        User registeredUser = new User();
        boolean regErrorResult = true;
        /*
        if (userName == null || userName.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_USER_NAME);
            return;
        }*/
        try {
            User user = findUserByName(userName);
            if (user != null) {
                setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_USER, regErrorResult);
                return;
            }
        } catch (PoolException | DaoException e) {
            //todo generate error page
        }
        registeredUser.setUserName(userName);
        paramName = resourcesManager.getProperty(PARAM_PASSWORD);
        String firstPassword = content.getRequestParameter(paramName);
        paramName = resourcesManager.getProperty(PARAM_PASSWORD_SECOND);
        String secondPassword = content.getRequestParameter(paramName);
       /*
        if (firstPassword == null || firstPassword.isEmpty() || secondPassword == null || secondPassword.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EMPTY_PASSWORD);
            return;
        }*/
        if (!firstPassword.equals(secondPassword)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_DIFFERENT_PASSWORDS, regErrorResult);
            return;
        }
        registeredUser.setUserPassword(getHashedPassword(firstPassword));
        paramName = resourcesManager.getProperty(PARAM_EMAIL);
        String email = content.getRequestParameter(paramName);
        /*
        if (!validateUserEmail(email)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_EMAIL);
            return;
        }*/
        try {
            User user = findUserByEmail(email);
            if (user != null) {
                setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_EMAIL, regErrorResult);
                return;
            }
        } catch (PoolException | DaoException e) {
            //todo generate error page
        }
        registeredUser.setEmail(email);
        registeredUser.setBlockedStatus(DEFAULT_BLOCKED_STATUS);
        registeredUser.setDiscount(DEFAULT_USER_DISCOUNT);
        registeredUser.setRoleId(DEFAULT_USER_ROLE_ID);
        String resultPage = resourcesManager.getProperty(LOGIN_PAGE);
        try {
            addRegisteredUser(registeredUser);
        } catch (PoolException | DaoException e) {
            // todo generate error page
        }
        regErrorResult = false;
        content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_RESULT), regErrorResult);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        content.pushAttributesToRequest(content.getRequest());
        content.pushAttributesToSession(content.getRequest());
    }

    private void setWrongRegistrationResult(SessionRequestContent content, String message, boolean regErrorResult) {
        String resultPageUrl = resourcesManager.getProperty(REGISTER_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPageUrl);
        content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_ERROR_MESSAGE), message);
        content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_RESULT), regErrorResult);
    }

    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    /*
    private boolean checkLogin(String username, String password) throws PoolException, DaoException {
        if (username.isEmpty() || username == null || password.isEmpty() || password == null) {
            return false;
        }
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findUserByName(username);
        pool.releaseConnection(connection);
        boolean result = false;
        if (user != null) {
            String userPassword = getHashedPassword(password);
            String dbPassword = user.getUserPassword();
            if (userPassword.equals(dbPassword)) {
                result = true;
            }
        }
        return result;
    }*/

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

    /*
    private boolean validateUserEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }*/

    public boolean addRegisteredUser(User user) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        boolean result = userDAO.addNewUser(user);
        pool.releaseConnection(connection);
        return result;
    }
}
