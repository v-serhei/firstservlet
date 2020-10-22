package by.verbitsky.servletdemo.entity.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.UserBuilder;

public class UserBuilderImpl implements UserBuilder {
    private String userName;
    private String email;
    private int discount;
    private int roleId;
    private int blockedStatus;
    private boolean loginStatus;

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public void setBlockedStatus(int blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    @Override
    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public User buildUser() {
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setDiscount(discount);
        user.setRoleId(roleId);
        user.setBlockedStatus(blockedStatus);
        user.setLoginStatus(loginStatus);
        return user;
    }
}
