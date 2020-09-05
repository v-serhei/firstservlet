package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.WebCommand;
import by.verbitsky.servletdemo.service.AuthService;
import by.verbitsky.servletdemo.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements WebCommand {
    private static final String USER_NAME = "username";
    private AuthService service = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        service.logout(request.getParameter(USER_NAME));
        request.setAttribute(USER_NAME, "guest");
        return "/main";
    }
}
