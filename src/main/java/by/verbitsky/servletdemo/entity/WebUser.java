package by.verbitsky.servletdemo.entity;

import javax.servlet.http.HttpSession;

public class WebUser {
    private HttpSession session;
    private String userName;
    private String email;
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public WebUser(HttpSession session, String userName, String email) {
        this.session = session;
        this.userName = userName;
        this.email = email;
    }
    public WebUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
    public WebUser(String userName, String password, String email) {
        this.userName = userName;
        this.email = email;
        this.userPassword = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebUser)) return false;

        WebUser webUser = (WebUser) o;

        if (session != null ? !session.equals(webUser.session) : webUser.session != null) return false;
        return userName != null ? userName.equals(webUser.userName) : webUser.userName == null;
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
        return sb.toString();
    }
}
