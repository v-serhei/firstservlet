package by.verbitsky.servletdemo.projectconst;

public class AttributesNames {

    //Session attributes
    public static final String SESSION_ATTR_USER = "user";
    public static final String SESSION_ATTR_LOCALE = "locale";
    public static final String SESSION_ATTR_LAST_COMMAND = "lastCommand";
    //public static final String SESSION_ATTR_LAST_URI = "lastUri";

    //Request attributes
    public static final String REQUEST_ATTR_LOGIN_FAILED = "loginFail";
    public static final String REQUEST_ATTR_ERROR_MSG = "errorMessage";
    public static final String REQUEST_ATTR_OPERATION_RESULT_MSG = "resultMessage";
    private AttributesNames() {
    }
}
