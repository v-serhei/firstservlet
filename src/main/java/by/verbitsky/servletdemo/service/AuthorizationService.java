package by.verbitsky.servletdemo.service;

import javax.servlet.http.HttpSession;

public interface AuthorizationService {
    boolean checkLogin(String username, String password);

    void logout(HttpSession userSession);
}
