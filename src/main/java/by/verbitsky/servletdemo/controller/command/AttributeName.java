package by.verbitsky.servletdemo.controller.command;

public class AttributeName {

    /**
     * Session attributes
     */
    public static final String SESSION_USER = "user";
    public static final String SESSION_LOCALE = "locale";
    public static final String SESSION_LAST_COMMAND = "lastCommand";
    public static final String SESSION_USER_LAST_QUERY = "lastUri";

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
     * Content attributes
     */
    public static final String SONG_CONTENT = "songContentList";
    public static final String SONG_CONTROLS_LINK = "songLinkValue";
    public static final String SONG_FILTER = "songFilter";
    public static final String SONG_TOTAL_PAGE_COUNT = "songTotalPageCount";
    public static final String SONG_SEARCH_COUNT_RESULT = "songContentCount";

    public static final String COMPILATION_CONTENT = "compilationContentList";
    public static final String COMPILATION_CONTROLS_LINK = "compilationLinkValue";
    public static final String COMPILATION_FILTER = "compilationFilter";
    public static final String COMPILATION_TOTAL_PAGE_COUNT = "compilationTotalPageCount";
    public static final String COMPILATION_SEARCH_COUNT_RESULT = "compilationContentCount";
    public static final String COMPILATION_TYPES_LIST = "compilationTypeList";



    public static final String ADDITIONAL_CONTENT_SONGS = "reviewSongList";
    public static final String REVIEW_CONTENT = "reviewContentList";
    public static final String REVIEW_CONTROLS_LINK = "reviewLinkValue";
    public static final String REVIEW_FILTER = "reviewFilter";
    public static final String REVIEW_TOTAL_PAGE_COUNT = "reviewTotalPageCount";
    public static final String REVIEW_SEARCH_COUNT_RESULT = "reviewContentCount";

    public static final String SINGER_LIST = "singerList";
    public static final String GENRE_LIST = "genreList";
    //search count result

    /**
     * Attribute names for error page
     */
    public static final String REQUESTED_URL = "requestedURL";
    public static final String COMMAND_ERROR_MESSAGE = "commandErrorMessage";


    public static final String ADMIN_OPERATION_RESULT_MSG = "resultMessage";

    private AttributeName() {
    }
}
