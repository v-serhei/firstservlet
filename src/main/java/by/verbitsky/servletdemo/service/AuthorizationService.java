package by.verbitsky.servletdemo.service;

import javax.servlet.http.HttpSession;

public interface AuthorizationService {
    boolean checkLogin(String username, String password);

    void logout(HttpSession userSession);

    boolean validateUserEmail(String email);

    boolean existUserEmail(String email);

    boolean existUserName(String email);
}
