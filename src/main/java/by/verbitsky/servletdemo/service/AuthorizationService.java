package by.verbitsky.servletdemo.service;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

public interface AuthorizationService {
    void processLogin(SessionRequestContent content);

    void processLogout(SessionRequestContent content);

    void processRegistration(SessionRequestContent content);
}
