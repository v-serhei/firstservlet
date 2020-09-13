package by.verbitsky.servletdemo.service;

import by.verbitsky.servletdemo.entity.WebUser;

import javax.servlet.http.HttpSession;

public interface AuthorizationService {
    boolean checkLogin(String username, String password);

    void logout(HttpSession userSession);

    boolean validateUserEmail(String email);

    boolean existUserEmail(String email);

    boolean existUserName(String userName);

    boolean addRegisteredUser (WebUser user, String password);
}
