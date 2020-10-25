package by.verbitsky.servletdemo.controller.command;

public class AttributeName {

    /**
     * Session attributes
     */
    public static final String SESSION_USER = "user";
    public static final String SESSION_LOCALE = "locale";
    public static final String SESSION_LAST_COMMAND = "lastCommand";
    public static final String SESSION_LAST_URI = "lastUri";

    /**
     * Request attributes
     */
    public static final String ADMIN_PAGE_ACCESS_DENIED = "admin.page.request.error";
    public static final String LOGIN_FAILED = "loginFail";
    public static final String REGISTRATION_WRONG_NAME = "regWrongName";
    public static final String REGISTRATION_EXIST_NAME = "regExistName";
    public static final String REGISTRATION_WRONG_PASSWORD = "regWrongPassword";
    public static final String REGISTRATION_DIFFERENT_PASSWORDS = "regDifferentPasswords";
    public static final String REGISTRATION_WRONG_EMAIL = "regWrongEmail";
    public static final String REGISTRATION_EXIST_EMAIL = "regExistEmail";

    /**
     * Pagination request attributes
     */

    public static final String CONTENT_CURRENT_PAGE_NUMBER = "currentPage";
    public static final String CONTENT_TOTAL_PAGE_COUNT = "totalPageCount";
    public static final String CONTENT = "contentList";
    public static final String CONTENT_FILTER = "filter";
    public static final String PAGINATION_CONTROLS_LINK = "linkValue";

    /**
     * List attributes for select tag
     */

    public static final String SINGER_LIST = "singerList";
    public static final String GENRE_LIST = "genreList";



    /**
     * Attribute names for error page
     */
    public static final String REQUESTED_URL = "requestedURL";
    public static final String COMMAND_ERROR_MESSAGE = "commandErrorMessage";


    public static final String ADMIN_OPERATION_RESULT_MSG = "resultMessage";

    private AttributeName() {
    }
}