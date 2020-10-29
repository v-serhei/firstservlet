package by.verbitsky.servletdemo.entity;

public class User {
    private String userName;
    private String email;
    private int discount;
    private int roleId;
    private int blockedStatus;
    private boolean loginStatus;
    private Basket basket;

    public User() {
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

    public void initBasket () {
        basket = new Basket();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;
        return userName != null ? userName.equals(user.userName) : user.userName == null;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
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
        }else {
            sb.append("false");
        }
        sb.append(", personal discount=");
        sb.append(discount);
        return sb.toString();
    }
}
