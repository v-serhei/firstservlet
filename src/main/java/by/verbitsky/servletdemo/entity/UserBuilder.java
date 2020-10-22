package by.verbitsky.servletdemo.entity;

public interface UserBuilder {
    void setUserName(String userName);

    void setEmail(String email);

    void setDiscount(int discount);

    void setRoleId(int roleId);

    void setBlockedStatus(int blockedStatus);

    void setLoginStatus(boolean loginStatus);

    User buildUser();
}
