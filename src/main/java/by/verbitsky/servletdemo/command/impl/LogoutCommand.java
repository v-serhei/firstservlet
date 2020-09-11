package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.service.AuthorizationService;
import by.verbitsky.servletdemo.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private static final String USER_NAME = "username";
    private AuthorizationService service = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        service.logout(request.getParameter(USER_NAME));
        request.setAttribute(USER_NAME, "guest");
        return "/main";
    }
}
