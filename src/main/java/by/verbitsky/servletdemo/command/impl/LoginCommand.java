package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.WebCommand;
import by.verbitsky.servletdemo.service.AuthService;
import by.verbitsky.servletdemo.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements WebCommand {
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";

    private AuthService service = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        String userName = request.getParameter(USER_NAME);
        String password = request.getParameter(USER_PASSWORD);
        boolean res = service.checkLogin(userName, password);
        if (res) {
            request.setAttribute(USER_NAME, userName);
            request.setAttribute("authBlockVisible", "none"); //лучше передать через атрибут сессии
            request.setAttribute("errorVisible", "none"); //лучше передать через атрибут сессии
            request.setAttribute("logoutBlockVisible", "block");
        } else {
            request.setAttribute(USER_NAME, "guest");
            request.setAttribute("logoutBlockVisible", "none");
            request.setAttribute("authBlockVisible", "block"); //лучше передать через атрибут сессии
            request.setAttribute("errorVisible", "block");
        }
        return "/main";
    }
}
