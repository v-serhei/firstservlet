package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.util.WebResourcesManager;

public class LoginResult extends CommandResult {
    private static final String DISPLAY_VALUE_TRUE = "block";
    private static final String DISPLAY_VALUE_FALSE = "none";
    private static final String LOGIN_BLOCK_PARAMETER_NAME = "attr.loginblockdisplay";
    private static final String LOGOUT_BLOCK_PARAMETER_NAME = "attr.logoutblockdisplay";
    private static final String USER_GREETING_PARAMETER_NAME = "attr.usergreeting";
    private static final String LOGIN_ERROR_MESSAGE_PARAMETER_NAME = "attr.login.error";
    private static final String RESULT_PAGE_PARAMETER_NAME = "attr.result.page";

    public void setDisplayLoginBlock(boolean visible) {
        if (visible) {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK_PARAMETER_NAME), DISPLAY_VALUE_TRUE);
        } else {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK_PARAMETER_NAME), DISPLAY_VALUE_FALSE);
        }
    }

    public void setDisplayLogoutBlock(boolean visible) {
        if (visible) {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGOUT_BLOCK_PARAMETER_NAME), DISPLAY_VALUE_TRUE);
        } else {
            addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_BLOCK_PARAMETER_NAME), DISPLAY_VALUE_FALSE);
        }
    }

    public void setUserGreetings(String text) {
        addAttribute(WebResourcesManager.getInstance().getProperty(USER_GREETING_PARAMETER_NAME), text);
    }

    public void setResultUrl(String resultUrl) {
        addAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE_PARAMETER_NAME), resultUrl);
    }

    public void setErrorMessage (String message) {
        addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR_MESSAGE_PARAMETER_NAME), message);
    }
}
