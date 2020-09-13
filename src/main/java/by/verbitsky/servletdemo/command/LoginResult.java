package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.util.WebResourcesManager;

public class LoginResult extends CommandResult {
    private static final String DISPLAY_VALUE_TRUE = "block";
    private static final String DISPLAY_VALUE_FALSE = "none";
    private static final String LOGIN_BLOCK = "attr.loginblockdisplay";
    private static final String LOGOUT_BLOCK = "attr.logoutblockdisplay";
    private static final String USER_GREETING = "attr.usergreeting";
    private static final String LOGIN_ERROR_MESSAGE = "attr.login.error";
    private static final String RESULT_PAGE = "attr.result.page";

    public void setDisplayLoginBlock(boolean visible) {
        if (visible) {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_TRUE);
        } else {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_FALSE);
        }
    }

    public void setDisplayLogoutBlock(boolean visible) {
        if (visible) {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK), DISPLAY_VALUE_TRUE);
        } else {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK), DISPLAY_VALUE_FALSE);
        }
    }

    public void setUserGreetings(String text) {
        addAttribute(WebResourcesManager.getInstance().getProperty(USER_GREETING), text);
    }

    public void setResultUrl(String resultUrl) {
        addAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultUrl);
    }

    public void setErrorMessage (String message) {
        addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR_MESSAGE), message);
    }
}
