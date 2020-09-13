package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.command.LoginResult;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String MAIN_PAGE = "pages.jsp.main";
    private static final String LOGIN_PAGE = "pages.jsp.login";
    private static final String USER_NAME = "attr.user.name";
    private static final String PASSWORD = "attr.password";
    private static final String HELLO_MESSAGE = "Hello, ";
    private static final String DEFAULT_USERNAME = "guest";
    public static final String LOGIN_ERROR_MESSAGE = "Wrong user name or password";

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String paramName = WebResourcesManager.getInstance().getProperty(USER_NAME);
        String userName = request.getParameter(paramName);
        String paramPassword = WebResourcesManager.getInstance().getProperty(PASSWORD);
        String password = request.getParameter(paramPassword);
        boolean checkLogin = UserService.INSTANCE.checkLogin(userName, password);

        String page;
        LoginResult commandResult = new LoginResult();
        if (checkLogin) {
            commandResult.setDisplayLoginBlock(false);
            commandResult.setDisplayLogoutBlock(true);
            commandResult.setUserGreetings(HELLO_MESSAGE.concat(userName));
            page = WebResourcesManager.getInstance().getProperty(MAIN_PAGE);
            commandResult.setResultUrl(page);
            UserService.INSTANCE.updateLoginAttributes (request.getSession(false), userName);
        } else {
            commandResult.setDisplayLoginBlock(true);
            commandResult.setDisplayLogoutBlock(false);
            commandResult.setUserGreetings(HELLO_MESSAGE.concat(DEFAULT_USERNAME));
            page = WebResourcesManager.getInstance().getProperty(LOGIN_PAGE);
            commandResult.setResultUrl(page);
            commandResult.setErrorMessage(LOGIN_ERROR_MESSAGE);
        }
        return commandResult;
    }
}
