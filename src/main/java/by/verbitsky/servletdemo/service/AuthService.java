package by.verbitsky.servletdemo.service;

public interface AuthService {
    void login(String username, String password);

    void logout(String username);
}
