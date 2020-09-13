package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.command.RegisterResult;
import by.verbitsky.servletdemo.entity.WebUser;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {
    private static final String LOGIN_PAGE = "pages.jsp.login";
    private static final String REGISTER_PAGE = "pages.jsp.registration";
    private static final String USER_NAME = "attr.user.name";
    private static final String PASSWORD = "attr.password";
    private static final String PASSWORD_SECOND = "attr.password.second";
    private static final String EMAIL = "attr.user.email";

    private static final String REGISTER_ERROR_EXIST_USER = "User already exists";
    private static final String REGISTER_ERROR_WRONG_USER_NAME = "Wrong user name";
    private static final String REGISTER_ERROR_DIFFERENT_PASSWORDS = "Different passwords";
    private static final String REGISTER_ERROR_EMPTY_PASSWORD = "Empty password field";
    private static final String REGISTER_ERROR_WRONG_EMAIL = "Wrong user email";
    private static final String REGISTER_ERROR_EXIST_EMAIL = "Email already used";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        RegisterResult result = new RegisterResult();
        String paramName = WebResourcesManager.getInstance().getProperty(USER_NAME);
        String userName = request.getParameter(paramName);
        if (userName == null || userName.isEmpty()) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            result.setErrorMessage(REGISTER_ERROR_WRONG_USER_NAME);
            return result;
        }
        if (UserService.INSTANCE.existUserName(userName)) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            result.setErrorMessage(REGISTER_ERROR_EXIST_USER);
            return result;
        }
        paramName = WebResourcesManager.getInstance().getProperty(PASSWORD);
        String firstPassword = request.getParameter(paramName);
        paramName = WebResourcesManager.getInstance().getProperty(PASSWORD_SECOND);
        String secondPassword = request.getParameter(paramName);
        if (firstPassword == null || firstPassword.isEmpty() || secondPassword == null || secondPassword.isEmpty()) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            result.setErrorMessage(REGISTER_ERROR_EMPTY_PASSWORD);
            return result;
        }
        if (!firstPassword.equals(secondPassword)) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            result.setErrorMessage(REGISTER_ERROR_DIFFERENT_PASSWORDS);
            return result;
        }
        paramName = WebResourcesManager.getInstance().getProperty(EMAIL);
        String email = request.getParameter(paramName);
        if (!UserService.INSTANCE.validateUserEmail(email)) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            result.setErrorMessage(REGISTER_ERROR_WRONG_EMAIL);
            return result;
        }
        if(UserService.INSTANCE.existUserEmail(email)) {
            String page = WebResourcesManager.getInstance().getProperty(REGISTER_PAGE);
            result.setResultUrl(page);
            System.out.println(page);
            result.setErrorMessage(REGISTER_ERROR_EXIST_EMAIL);
            return result;
        }
        String page = WebResourcesManager.getInstance().getProperty(LOGIN_PAGE);
        WebUser user = new WebUser(request.getSession(false),userName, email);
        UserService.INSTANCE.addRegisteredUser(user, firstPassword);
        result.setResultUrl(page);
        return result;
    }
}
