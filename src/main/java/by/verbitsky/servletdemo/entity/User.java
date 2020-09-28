package by.verbitsky.servletdemo.entity;

import javax.servlet.http.HttpSession;

public class User {
    private HttpSession session;
    private String userName;
    private String email;
    private String userPassword;
    private int discount;
    private int roleId;
    private int blockedStatus;
    private boolean loginStatus;

    public User() {
    }

    public User(String userName, String email, String userPassword, int discount, int roleId, int blockedStatus) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
        this.discount = discount;
        this.roleId = roleId;
        this.blockedStatus = blockedStatus;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public int getBlockedStatus() {
        return blockedStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (session != null ? !session.equals(user.session) : user.session != null) return false;
        return userName != null ? userName.equals(user.userName) : user.userName == null;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }


    /*@Override
    public String toString() {

        //todo fix it
        StringBuilder  sb = new StringBuilder();
        sb.append("User: name=");
        sb.append(userName);
        sb.append(", email=");
        sb.append(email);

        return sb.toString();
    }*/

    @Override
    public String toString() {
        return "User{" +
                "session=" + session +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", discount=" + discount +
                ", roleId=" + roleId +
                ", blockedStatus=" + blockedStatus +
                ", isLoggedIn=" + loginStatus +
                '}';
    }
}
