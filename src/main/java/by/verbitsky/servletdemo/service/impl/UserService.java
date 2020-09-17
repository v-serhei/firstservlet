package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.dao.UserDAO;
import by.verbitsky.servletdemo.entity.WebUser;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPool;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.service.AuthorizationService;
import by.verbitsky.servletdemo.service.SessionService;
import by.verbitsky.servletdemo.service.WebResourcesManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

public enum UserService implements AuthorizationService, SessionService {
    INSTANCE;
    private static final String ATTR_SESSION_LOGIN_RESULT = "attr.session.loginresult";
    private static final String ATTR_SESSION_USER = "attr.session.user";
    private static final String DEFAULT_GREETINGS = "Hello, guest";
    private static final String DEFAULT_USER_EMAIL = "default";
    private static final String DEFAULT_USER_NAME = "guest";
    private static final String DISPLAY_VALUE_TRUE = "block";
    private static final String DISPLAY_VALUE_FALSE = "none";
    private static final String EMAIL = "attr.user.email";
    private static final String EMPTY_LOGIN_ERROR_MESSAGE = "";
    private static final String HELLO_MESSAGE = "Hello, ";
    private static final String LOGIN_BLOCK = "attr.loginblockdisplay";
    private static final String LOGIN_ERROR = "attr.login.error";
    private static final String LOGIN_ERROR_MESSAGE = "Wrong user name or password";
    private static final String LOGIN_PAGE = "pages.jsp.login";
    private static final String LOGOUT_BLOCK = "attr.logoutblockdisplay";
    private static final String MAIN_PAGE = "pages.jsp.main";
    private static final String NEGATIVE_LOGIN_RESULT = "false";
    private static final String PASSWORD = "attr.password";
    private static final String PASSWORD_SECOND = "attr.password.second";
    private static final String POSITIVE_LOGIN_RESULT = "true";
    private static final String REGISTER_ERROR_DIFFERENT_PASSWORDS = "Different passwords";
    private static final String REGISTER_ERROR_EMPTY_PASSWORD = "Empty password field";
    private static final String REGISTER_ERROR_EXIST_EMAIL = "Email already used";
    private static final String REGISTER_ERROR_EXIST_USER = "User already exists";
    private static final String REGISTER_ERROR_WRONG_EMAIL = "Wrong user email";
    private static final String REGISTER_ERROR_WRONG_USER_NAME = "Wrong user name";
    private static final String REGISTER_PAGE = "pages.jsp.registration";
    private static final String RESULT_PAGE = "attr.result.page";
    private static final String USER_GREETING = "attr.usergreeting";
    private static final String USER_NAME = "attr.user.name";

    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    private final Logger logger = LogManager.getLogger();
    private static ConnectionPool pool = ConnectionPool.getInstance();
    //todo сделать дао переменной метода
    private UserDAO userDAO = new UserDAO();

/*
    public void updateLoginAttributes(String userName) {
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
*/
    @Override
    public void processLogin(SessionRequestContent content) {
        String paramName = WebResourcesManager.getInstance().getProperty(USER_NAME);
        String userName = content.getRequestParameter(paramName);
        String paramPassword = WebResourcesManager.getInstance().getProperty(PASSWORD);
        String password = content.getRequestParameter(paramPassword);
        boolean checkLogin = UserService.INSTANCE.checkLogin(userName, password);
        String resultPage;
        if (checkLogin) {
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_FALSE);
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_TRUE);
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(USER_GREETING), HELLO_MESSAGE.concat(userName));
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(ATTR_SESSION_LOGIN_RESULT), POSITIVE_LOGIN_RESULT);
            content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR), EMPTY_LOGIN_ERROR_MESSAGE);
            resultPage = WebResourcesManager.getInstance().getProperty(MAIN_PAGE);
            content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultPage);
        } else {
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_TRUE);
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_FALSE);
            content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(USER_GREETING), DEFAULT_GREETINGS);
            content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR), LOGIN_ERROR_MESSAGE);
            resultPage = WebResourcesManager.getInstance().getProperty(LOGIN_PAGE);
            content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultPage);
        }
    }

    @Override
    public void processLogout(SessionRequestContent content) {
        content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_TRUE);
        content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_FALSE);
        content.addSessionAttribute(WebResourcesManager.getInstance().getProperty(USER_GREETING), DEFAULT_GREETINGS);
        String resultPageUrl = WebResourcesManager.getInstance().getProperty(MAIN_PAGE);
        content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultPageUrl);
    }

    @Override
    public void processRegistration(SessionRequestContent content) {
        String paramName = WebResourcesManager.getInstance().getProperty(USER_NAME);
        String userName = content.getRequestParameter(paramName);
        if (userName == null || userName.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_WRONG_USER_NAME);
            return;
        }
        if (existUserName(userName)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_EXIST_USER);
            return;
        }
        paramName = WebResourcesManager.getInstance().getProperty(PASSWORD);
        String firstPassword = content.getRequestParameter(paramName);
        paramName = WebResourcesManager.getInstance().getProperty(PASSWORD_SECOND);
        String secondPassword = content.getRequestParameter(paramName);
        if (firstPassword == null || firstPassword.isEmpty() || secondPassword == null || secondPassword.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_EMPTY_PASSWORD);
            return;
        }
        if (!firstPassword.equals(secondPassword)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_DIFFERENT_PASSWORDS);
            return;
        }
        paramName = WebResourcesManager.getInstance().getProperty(EMAIL);
        String email = content.getRequestParameter(paramName);
        if (!validateUserEmail(email)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_WRONG_EMAIL);
            return;
        }
        if (existUserEmail(email)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_EXIST_EMAIL);
            return;
        }
        String resultPage = WebResourcesManager.getInstance().getProperty(LOGIN_PAGE);
        WebUser user = new WebUser(content.getSession(), userName, email);
        addRegisteredUser(user, firstPassword);
        content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultPage);

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
                session.setAttribute(attrName, NEGATIVE_LOGIN_RESULT);
                attrName = WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_TRUE);
                attrName = WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_FALSE);
                attrName = WebResourcesManager.getInstance().getProperty(USER_GREETING);
                session.setAttribute(attrName, HELLO_MESSAGE.concat(user.getUserName()));
            }
        }
    }

    private void setWrongRegistrationResult(SessionRequestContent content, String error) {
        String resultPageUrl = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
        content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultPageUrl);
        content.addRequestAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR_MESSAGE), error);
    }

    private String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    private boolean checkLogin(String username, String password) {
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
            //todo log this and throw exception
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

    private boolean existUserEmail(String email) {
        ProxyConnection connection;
        boolean result = true;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.existUserEmail(email.toLowerCase());
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo создать результат перенаправления на страницу ошибки
            //todo log this and throw exception
        }
        return result;
    }

    private boolean existUserName(String userName) {
        ProxyConnection connection;
        boolean result = true;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.existUserName(userName.toLowerCase());
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo log this and throw exception
        }
        return result;
    }

    private boolean validateUserEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean addRegisteredUser(WebUser user, String password) {
        ProxyConnection connection;
        String hashedPassword = getHashedPassword(password);
        boolean result = false;
        try {
            connection = pool.getConnection();
            userDAO.setConnection(connection);
            result = userDAO.addNewUser(user, hashedPassword);
            pool.releaseConnection(connection);
        } catch (PoolException e) {
            //todo log this and throw exception
        }
        return result;
    }
}
