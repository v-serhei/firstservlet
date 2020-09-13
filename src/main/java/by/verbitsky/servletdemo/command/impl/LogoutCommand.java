package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.command.LogoutResult;
import by.verbitsky.servletdemo.service.AuthorizationService;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private static final String USER_NAME = "username";
    private static final String DEFAULT_GREETINGS = "Hello, guest";
    private static final String MAIN_PAGE_PARAMETER_NAME = "pages.jsp.main";
    private AuthorizationService service = new UserService();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        service.logout(request.getParameter(USER_NAME));
        LogoutResult result = new LogoutResult();
        String page = WebResourcesManager.getInstance().getProperty(MAIN_PAGE_PARAMETER_NAME);
        result.setDisplayLoginBlock(true);
        result.setDisplayLogoutBlock(false);
        result.setUserGreetings(DEFAULT_GREETINGS);
        result.setResultUrl(page);
        return result;
    }
}
