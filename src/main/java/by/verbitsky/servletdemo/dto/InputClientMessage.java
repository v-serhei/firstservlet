package by.verbitsky.servletdemo.dto;

import java.io.Serializable;

public class InputClientMessage implements Serializable {
    private static final long serialVersionUID = 9174266213781970671L;

    private String message;
    private String action;
    private String page;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public InputClientMessage(String message, String action, String page) {
        this.message = message;
        this.action = action;
        this.page = page;
    }
}
