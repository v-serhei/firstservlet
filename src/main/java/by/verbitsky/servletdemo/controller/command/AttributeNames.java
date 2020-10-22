package by.verbitsky.servletdemo.controller.command;

public class AttributeNames {

    /**
     * Session attributes
     */
    public static final String SESSION_ATTR_USER = "user";
    public static final String SESSION_ATTR_LOCALE = "locale";
    public static final String SESSION_ATTR_LAST_COMMAND = "lastCommand";
    public static final String SESSION_ATTR_LAST_URI = "lastUri";

    /**
     * Request attributes
     */
    public static final String REQUEST_ATTR_ADMIN_PAGE_ACCESS_DENIED = "admin.page.request.error";
    public static final String REQUEST_ATTR_LOGIN_FAILED = "loginFail";
    public static final String REQUEST_ATTR_REGISTRATION_NAME_ERROR_MSG = "regNameErrorMessage";
    public static final String REQUEST_ATTR_REGISTRATION_PASSWORD_ERROR_MSG = "regPasswordErrorMessage";
    public static final String REQUEST_ATTR_REGISTRATION_EMAIL_ERROR_MSG = "regEmailErrorMessage";
    public static final String REQUEST_ATTR_REGISTRATION_FAIL_WRONG_NAME = "regErrorName";
    public static final String REQUEST_ATTR_REGISTRATION_FAIL_WRONG_PASSWORD = "regErrorPassword";
    public static final String REQUEST_ATTR_REGISTRATION_FAIL_WRONG_EMAIL = "regErrorEmail";

    /**
     * Attribute names for error page
     */
    public static final String REQUEST_ATTR_REQUESTED_URL = "requestedURL";
    public static final String REQUEST_ATTR_COMMAND_ERROR_MESSAGE = "commandErrorMessage";


    public static final String REQUEST_ATTR_ADMIN_OPERATION_RESULT_MSG = "resultMessage";

    private AttributeNames() {
    }
}
