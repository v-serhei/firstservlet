package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.service.AuthorizationService;

public class UserService implements AuthorizationService {
    //stub for DAO
    private static final String USER_NAME = "admin";
    private static final String USER_PASSWORD = "admin";

    @Override
    public boolean checkLogin(String username, String password) {
        if (username.isEmpty() ||  username==null || password.isEmpty() || password == null) {
            return false;
        }
        boolean res = false;
        if (username.equals(USER_NAME) && password.equals(USER_PASSWORD))
        {
            res=true;
        }
        return res;
    }

    @Override
    public void logout(String username) {
        //stub logout
    }
}
