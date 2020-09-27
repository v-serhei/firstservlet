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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.Locale;

public enum UserService {
    INSTANCE;
    //todo вынести в ресурсы

    private static final String ATTR_SESSION_USER = "attr.session.user";
    private static final String ATTR_SESSION_LOCALE = "attr.session.locale";
    private static final String EMAIL = "attr.user.email";
    private static final String LOGIN_PAGE = "pages.jsp.login";
    private static final String MAIN_PAGE = "pages.jsp.main";
    private static final String PASSWORD = "attr.password";
    private static final String PASSWORD_SECOND = "attr.password.second";
    private static final String REGISTER_ERROR_ATTRIBUTE = "attr.reg.error";
    private static final String REGISTER_ERROR_MESSAGE_DIFFERENT_PASSWORDS = "reg.error.message.different.passwords";
    //private static final String REGISTER_ERROR_MESSAGE_EMPTY_PASSWORD = "Empty password field";
    private static final String REGISTER_ERROR_MESSAGE_EXIST_EMAIL = "reg.error.message.exist.email";
    private static final String REGISTER_ERROR_MESSAGE_EXIST_USER = "reg.error.message.exist.user";
    //private static final String REGISTER_ERROR_MESSAGE_WRONG_EMAIL = "Wrong user email";
    //private static final String REGISTER_ERROR_MESSAGE_WRONG_USER_NAME = "Wrong user name";
    private static final String REGISTER_PAGE = "pages.jsp.registration";
    private static final String RESULT_PAGE = "attr.result.page";

    private static final String USER_NAME = "attr.user.name";


    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    private static final int DEFAULT_USER_DISCOUNT = 0;
    private static final int DEFAULT_USER_ROLE_ID = 2;
    private static final int DEFAULT_BLOCKED_STATUS = 0;


    private final Logger logger = LogManager.getLogger();
    private final ConnectionPoolImpl pool = ConnectionPoolImpl.getInstance();
    private final WebResourcesManager resourcesManager = WebResourcesManager.getInstance();

  /*
    public void processLanguageSwitch(SessionRequestContent content) {
        System.out.println("process language");
        InputClientMessage message = (InputClientMessage) content.getRequestAttribute(MESSAGE_PARAMETER);
        Locale locale;
        System.out.println();
        switch (message.getMessage().toLowerCase()) {
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
        content.addSessionAttribute(LOCALE, locale);
        String resultPage = resourcesManager.getProperty(MAIN_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
    }*/

    public void processLogin(SessionRequestContent content) {
        String paramName = resourcesManager.getProperty(USER_NAME);
        String userName = content.getRequestParameter(paramName);
        String paramPassword = resourcesManager.getProperty(PASSWORD);
        String password = content.getRequestParameter(paramPassword);
        String resultPage;
        if (userName.isEmpty() || userName == null || password.isEmpty() || password == null) {
            resultPage = resourcesManager.getProperty(LOGIN_PAGE);
        } else {
            try {
                //looking for user in data base and compare user passwords
                User user = findUserByName(userName);
                if (user != null) {
                    //if passwords are equals - set logged in status true
                    if (user.getUserPassword().equals(getHashedPassword(password))) {
                        user.setLoggedIn(true);
                        user.setSession(content.getSession());
                        content.addSessionAttribute(ATTR_SESSION_USER, user);
                        resultPage = resourcesManager.getProperty(MAIN_PAGE);
                    } else {
                        resultPage = resourcesManager.getProperty(LOGIN_PAGE);
                    }
                } else {
                    resultPage = resourcesManager.getProperty(LOGIN_PAGE);
                }
            } catch (PoolException | DaoException e) {
                //todo подумать тут
                logger.log(Level.WARN, "UserService: process login  error", e);
                resultPage = resourcesManager.getProperty(LOGIN_PAGE);
            }
        }
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        /*boolean checkLogin = false;
        try {
            checkLogin = checkLogin(userName, password);
        } catch (PoolException | DaoException e) {
        }

        if (checkLogin) {
            User loggedInUser = findUserByName()


            content.addSessionAttribute(resourcesManager.getProperty(LOGIN_BLOCK), DISPLAY_VALUE_FALSE);
            content.addSessionAttribute(resourcesManager.getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_TRUE);
            content.addSessionAttribute(resourcesManager.getProperty(USER_GREETING), HELLO_MESSAGE.concat(userName));
            content.addSessionAttribute(resourcesManager.getProperty(ATTR_SESSION_LOGIN_RESULT), POSITIVE_LOGIN_RESULT);
            content.addRequestAttribute(resourcesManager.getProperty(LOGIN_ERROR), EMPTY_LOGIN_ERROR_MESSAGE);
            resultPage = resourcesManager.getProperty(MAIN_PAGE);
            content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        } else {
            content.addSessionAttribute(resourcesManager.getProperty(LOGIN_BLOCK), DISPLAY_VALUE_TRUE);
            content.addSessionAttribute(resourcesManager.getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_FALSE);
            content.addSessionAttribute(resourcesManager.getProperty(USER_GREETING), DEFAULT_GREETINGS);
            content.addRequestAttribute(resourcesManager.getProperty(LOGIN_ERROR), LOGIN_ERROR_MESSAGE);

            content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
        }*/
    }

    public void processLogout(SessionRequestContent content) {
        content.getSession().invalidate();
        /*
        content.addSessionAttribute(resourcesManager.getProperty(LOGIN_BLOCK), DISPLAY_VALUE_TRUE);
        content.addSessionAttribute(resourcesManager.getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_FALSE);
        content.addSessionAttribute(resourcesManager.getProperty(USER_GREETING), DEFAULT_GREETINGS);*/
        String resultPageUrl = resourcesManager.getProperty(MAIN_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPageUrl);
    }

    public void processRegistration(SessionRequestContent content) {
        String paramName = resourcesManager.getProperty(USER_NAME);
        String userName = content.getRequestParameter(paramName);
        User registeredUser = new User();
        /*
        if (userName == null || userName.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_USER_NAME);
            return;
        }*/
        try {
            User user = findUserByName(userName);
            if (user != null) {
                setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_USER);
                return;
            }
        } catch (PoolException | DaoException e) {
            //todo generate error page
        }
        registeredUser.setUserName(userName);
        paramName = resourcesManager.getProperty(PASSWORD);
        String firstPassword = content.getRequestParameter(paramName);
        paramName = resourcesManager.getProperty(PASSWORD_SECOND);
        String secondPassword = content.getRequestParameter(paramName);
       /*
        if (firstPassword == null || firstPassword.isEmpty() || secondPassword == null || secondPassword.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EMPTY_PASSWORD);
            return;
        }*/
        if (!firstPassword.equals(secondPassword)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_DIFFERENT_PASSWORDS);
            return;
        }
        registeredUser.setUserPassword(getHashedPassword(firstPassword));
        paramName = resourcesManager.getProperty(EMAIL);
        String email = content.getRequestParameter(paramName);
        /*
        if (!validateUserEmail(email)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_EMAIL);
            return;
        }*/
        try {
            User user = findUserByEmail(email);
            if (user != null) {
                setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_EMAIL);
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
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
    }

    public void processNewSession(HttpSession session) {
        if (session != null) {
            if (session.isNew()) {
                session.setMaxInactiveInterval(DEFAULT_SESSION_LIVE_TIME);
                User user = new User();
                user.setSession(session);
                user.setLoggedIn(false);
                Object locale = session.getAttribute(resourcesManager.getProperty(ATTR_SESSION_LOCALE));
                if (locale == null) {
                    session.setAttribute(resourcesManager.getProperty(ATTR_SESSION_LOCALE), Locale.getDefault());
                }
                session.setAttribute(ATTR_SESSION_USER, user);

                //todo del this
                /*
                String attrName = resourcesManager.getProperty(ATTR_SESSION_USER);
                User user = new User(session, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL);
                session.setAttribute(attrName, user);
                attrName = resourcesManager.getProperty(ATTR_SESSION_LOGIN_RESULT);
                session.setAttribute(attrName, NEGATIVE_LOGIN_RESULT);
                attrName = resourcesManager.getProperty(LOGIN_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_TRUE);
                attrName = resourcesManager.getProperty(LOGOUT_BLOCK);
                session.setAttribute(attrName, DISPLAY_VALUE_FALSE);
                attrName = resourcesManager.getProperty(USER_GREETING);
                session.setAttribute(attrName, HELLO_MESSAGE.concat(user.getUserName()));*/
            }
        }
    }

    private void setWrongRegistrationResult(SessionRequestContent content, String error) {
        String resultPageUrl = resourcesManager.getProperty(REGISTER_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPageUrl);
        content.addRequestAttribute(resourcesManager.getProperty(REGISTER_ERROR_ATTRIBUTE), error);
    }

    private String getHashedPassword(String password) {
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

    private User findUserByEmail(String email) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findByEmail(email.toLowerCase());
        pool.releaseConnection(connection);
        return user;
    }


    private User findUserByName(String userName) throws PoolException, DaoException {
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

    private boolean addRegisteredUser(User user) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        boolean result = userDAO.addNewUser(user);
        pool.releaseConnection(connection);
        return result;
    }
}
