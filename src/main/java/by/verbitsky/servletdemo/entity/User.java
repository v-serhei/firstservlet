package by.verbitsky.servletdemo.entity;

import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.time.LocalDate;

public class User {
    private long userId;
    private String userName;
    private String email;
    private int discount;
    private int roleId;
    private int blockedStatus;
    private boolean loginStatus;
    private Basket basket;
    private LocalDate registrationDate;
    private String roleDescription;


    public User() {
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
        setRoleDescription(UserServiceImpl.INSTANCE.getRoleNameById(roleId));
    }

    public boolean getBlockedStatus() {
        return blockedStatus > 0;
    }

    public void setBlockedStatus(int statusId) {
        //status 1 or more - blocked, status <=0 - non blocked
        if (statusId > 0) {
            blockedStatus = 1;
        }
        if (statusId <= 0) {
            blockedStatus = 0;
        }
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Basket getBasket() {
        return basket;
    }

    public void initBasket() {
        basket = new Basket();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;

        User user = (User) o;
        return userName != null ? userName.equals(user.userName) : user.userName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ userId >>> 32);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: name=");
        sb.append(userName);
        sb.append(", email=");
        sb.append(email);
        sb.append(", roleId=");
        sb.append(roleId);
        sb.append(", isBlocked=");
        if (blockedStatus > 0) {
            sb.append("true");
        } else {
            sb.append("false");
        }
        sb.append(", personal discount=");
        sb.append(discount);
        return sb.toString();
    }
}
