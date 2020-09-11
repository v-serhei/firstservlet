package by.verbitsky.servletdemo.service;

public interface AuthorizationService {
    boolean checkLogin(String username, String password);

    void logout(String username);
}
