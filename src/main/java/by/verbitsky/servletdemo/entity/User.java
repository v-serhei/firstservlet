package by.verbitsky.servletdemo.entity;

import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.time.LocalDate;

/**
 * Class User. Describes application user.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see Basket
 */
public class User {
    /**
     * user id in data base
     */
    private long userId;
    /**
     * contains user name, that's used as login
     */
    private String userName;
    /**
     * contains user email
     */
    private String email;
    /**
     * contains user's current personal discount
     */
    private int discount;
    /**
     * contains user role id
     */
    private int roleId;
    /**
     * contains user status id
     * if field value == 0 - status is "Active"
     * if field value > 0 - status is "Blocked"
     */
    private int blockedStatus;
    /**
     * flag for determination current user authorization status
     */
    private boolean loginStatus;
    /**
     * user temporary order storage
     */
    private Basket basket;
    /**
     * contains date of user registration
     */
    private LocalDate registrationDate;
    /**
     * user role text description
     */
    private String roleDescription;
    /**
     * value used as flag to providing additional application functions
     */
    private boolean isAdminRole;

    /**
     * Base Constructor
     */
    public User() {
    }

    public boolean isAdminRole() {
        return isAdminRole;
    }

    public void setAdminRoleFlag(boolean flag) {
        isAdminRole = flag;
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
