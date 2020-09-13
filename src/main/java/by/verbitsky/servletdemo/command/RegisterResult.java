package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.util.WebResourcesManager;

public class RegisterResult extends CommandResult {
    private static final String LOGIN_ERROR_MESSAGE = "attr.reg.error";
    private static final String RESULT_PAGE = "attr.result.page";

    public void setResultUrl(String resultUrl) {
        addAttribute(WebResourcesManager.getInstance().getProperty(RESULT_PAGE), resultUrl);
    }

    public void setErrorMessage (String message) {
        addAttribute(WebResourcesManager.getInstance().getProperty(LOGIN_ERROR_MESSAGE), message);
    }
}
