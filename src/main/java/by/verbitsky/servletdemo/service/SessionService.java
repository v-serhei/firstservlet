package by.verbitsky.servletdemo.service;

import javax.servlet.http.HttpSession;

public interface SessionService {
    void processNewSession(HttpSession session);
   // void updateLoginAttributes (HttpSession session,  String userName);
}
