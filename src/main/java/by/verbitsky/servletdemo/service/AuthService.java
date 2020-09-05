package by.verbitsky.servletdemo.service;

public interface AuthService {
    boolean checkLogin(String username, String password);

    void logout(String username);
}
