package by.verbitsky.servletdemo.controller.command;

public class AttributeName {

    /**
     * Session attributes
     */
    public static final String SESSION_USER = "user";
    public static final String SESSION_LOCALE = "locale";
    public static final String SESSION_USER_LAST_QUERY = "lastUri";
    public static final String SESSION_CURRENT_ORDER = "currentOrder";


    public static final String USER_UPDATE_FAILED = "updateUser";
    public static final String USER_PROFILE_SHOW_MESSAGE = "showMessage";

    //attributes for result page
    public static final String OPERATION_TYPE = "operationType";
    public static final String OPERATION_RESULT = "operationResult";
    public static final String OPERATION_MESSAGE = "operationMessage";
    public static final String OPERATION_BUTTON_CAPTION = "btnCaptionValue";
    public static final String OPERATION_BUTTON_LINK = "btnLinkValue";
    /**
     * Content attributes
     */
    //song content
    public static final String SONG_CONTENT = "songContentList";
    public static final String SONG_CONTROLS_LINK = "songLinkValue";
    public static final String SONG_FILTER = "songFilter";
    public static final String SONG_TOTAL_PAGE_COUNT = "songTotalPageCount";
    public static final String SONG_SEARCH_COUNT_RESULT = "songContentCount";
    //compilation content
    public static final String COMPILATION_CONTENT = "compilationContentList";
    public static final String COMPILATION_CONTROLS_LINK = "compilationLinkValue";
    public static final String COMPILATION_FILTER = "compilationFilter";
    public static final String COMPILATION_TOTAL_PAGE_COUNT = "compilationTotalPageCount";
    public static final String COMPILATION_SEARCH_COUNT_RESULT = "compilationContentCount";
    public static final String COMPILATION_TYPES_LIST = "compilationTypeList";

    //review content
    public static final String REVIEW_CREATION_SONG = "song";
    public static final String REVIEW_CREATION_SINGER = "singer";

    public static final String ADDITIONAL_CONTENT_SONGS = "reviewSongList";
    public static final String REVIEW_CONTENT = "reviewContentList";
    public static final String REVIEW_CONTROLS_LINK = "reviewLinkValue";
    public static final String REVIEW_FILTER = "reviewFilter";
    public static final String REVIEW_TOTAL_PAGE_COUNT = "reviewTotalPageCount";
    public static final String REVIEW_SEARCH_COUNT_RESULT = "reviewContentCount";
    //additional song content
    public static final String SINGER_LIST = "singerList";
    public static final String GENRE_LIST = "genreList";
    public static final String ALBUM_LIST = "albumList";
    //order request  attributes
    public static final String ORDER = "requestedOrder";
    public static final String SHOW_ORDER_CONTROLS = "enableOrderControls";

    public static final String ORDER_LIST = "orderList";
    public static final String ORDER_TOTAL_COUNT = "totalOrderCount";
    public static final String ORDER_TOTAL_PAID_COUNT = "totalPaidOrderCount";
    public static final String ORDER_TOTAL_PRICE = "totalOrderPrice";
    /**
     * Request attributes
     */
    public static final String ADMIN_PAGE_ACCESS_DENIED = "access.violation.message";
    public static final String LOGIN_FAILED = "loginFail";
    public static final String REGISTRATION_WRONG_NAME = "regWrongName";
    public static final String REGISTRATION_EXIST_NAME = "regExistName";
    public static final String REGISTRATION_WRONG_PASSWORD = "regWrongPassword";
    public static final String REGISTRATION_DIFFERENT_PASSWORDS = "regDifferentPasswords";
    public static final String REGISTRATION_WRONG_EMAIL = "regWrongEmail";
    public static final String REGISTRATION_EXIST_EMAIL = "regExistEmail";
    /**
     * Attribute names for error page
     */
    public static final String REQUESTED_URL = "requestedURL";
    public static final String COMMAND_ERROR_MESSAGE = "commandErrorMessage";

    /* admin attributes*/
    public static final String ADMIN_SELECTED_USER = "selectedUser";
    public static final String ADMIN_USER_ENABLE = "userFlag";
    public static final String ADMIN_USER_LIST = "userList";
    public static final String ADMIN_ROLE_LIST = "roleList";

    public static final String ADMIN_OPERATION_RESULT_MSG = "resultMessage";
    public static final String ADMIN_OPERATION_MESSAGE_FLAG = "enableMessage";

    private AttributeName() {
    }
}
