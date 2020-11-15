package by.verbitsky.servletdemo.controller.command;

public class PagePath {

    //redirect path
    public static final String REDIRECT_MAIN_PAGE = "/audiobox/do/main";
    public static final String REDIRECT_REVIEW_PAGE = "/audiobox/do/reviews";
    public static final String REDIRECT_LOGIN_PAGE = "/audiobox/do/login";
    public static final String REDIRECT_ORDER_PAGE = "/audiobox/do/order";
    public static final String REDIRECT_SETTINGS_PAGE = "/audiobox/do/profile/settings";
    public static final String REDIRECT_USER_ORDER_PAGE = "/audiobox/do/profile/user_orders";
    public static final String REDIRECT_USER_REVIEWS_PAGE = "/audiobox/do/profile/user_reviews";
    public static final String REDIRECT_ORDER_PAYMENT_RESULT_PAGE = "/audiobox/do/order/payment/operation_result";
    public static final String REDIRECT_REVIEW_CREATION_RESULT_PAGE = "/audiobox/do/review/create_review/operation_result";
    public static final String REDIRECT_ORDER_REMOVE_RESULT_PAGE = "/audiobox/do/profile/remove_order/operation_result";
    public static final String REDIRECT_ADMIN_USER_MANAGEMENT_PAGE = "/audiobox/do/admin/user_management";
    public static final String REDIRECT_ADMIN_REVIEW_MANAGEMENT_PAGE = "/audiobox/do/admin/review_management";
    public static final String REDIRECT_ADMIN_SINGER_MANAGEMENT_PAGE = "/audiobox/do/admin/singer_management";
    public static final String REDIRECT_ADMIN_GENRE_MANAGEMENT_PAGE = "/audiobox/do/admin/genre_management";
    public static final String REDIRECT_ADMIN_ALBUM_MANAGEMENT_PAGE = "/audiobox/do/admin/album_management";
    public static final String REDIRECT_ADMIN_COMPILATION_MANAGEMENT_PAGE = "/audiobox/do/admin/compilation_management";
    public static final String REDIRECT_ADMIN_COMPILATION_PAGE = "/audiobox/do/admin/compilation";
    public static final String REDIRECT_ADMIN_SONG_MANAGEMENT_PAGE = "/audiobox/do/admin/song_management";

    //path to jsp (for forwarding)
    public static final String FORWARD_ADMIN_USER_MANAGEMENT = "/pages/admin/usermanagement.jsp";
    public static final String FORWARD_ADMIN_REVIEW_MANAGEMENT = "/pages/admin/reviewmanagement.jsp";
    public static final String FORWARD_ADMIN_SONG_MANAGEMENT = "/pages/admin/songmanagement.jsp";
    public static final String FORWARD_ADMIN_SINGER_MANAGEMENT = "/pages/admin/singermanagement.jsp";
    public static final String FORWARD_ADMIN_GENRE_MANAGEMENT = "/pages/admin/genremanagement.jsp";
    public static final String FORWARD_ADMIN_ALBUM_MANAGEMENT = "/pages/admin/albummanagement.jsp";
    public static final String FORWARD_ADMIN_COMPILATION_MANAGEMENT = "/pages/admin/compilationmanagement.jsp";
    public static final String FORWARD_ADMIN_COMPILATION_CREATION = "/pages/admin/compilationpage.jsp";


    public static final String FORWARD_MAIN_PAGE = "/pages/common/mainpage.jsp";
    public static final String FORWARD_RESULT_PAGE = "/pages/user/result.jsp";
    public static final String FORWARD_REVIEW_PAGE = "/pages/common/reviews.jsp";
    public static final String FORWARD_COMPILATION_PAGE = "/pages/common/compilations.jsp";
    public static final String FORWARD_LOGIN_PAGE = "/pages/common/login.jsp";
    public static final String FORWARD_ORDER_PAGE = "/pages/user/order.jsp";
    public static final String FORWARD_REGISTRATION_PAGE = "/pages/common/registration.jsp";
    public static final String FORWARD_USER_SETTINGS_PAGE = "/pages/user/profilesettings.jsp";
    public static final String FORWARD_USER_ORDERS_PAGE = "/pages/user/profileorders.jsp";
    public static final String FORWARD_USER_REVIEWS_PAGE = "/pages/user/profilereviews.jsp";
    public static final String FORWARD_ADD_REVIEW_PAGE = "/pages/common/addreview.jsp";
    public static final String FORWARD_ERROR_PAGE = "/pages/error/errorPage.jsp";

    //pagination controls href attribute value
    public static final String PAGINATION_MAIN = "/do/main/next_page?filtered=true&contentType=song";
    public static final String PAGINATION_COMPILATION = "/do/compilations/next_page?filtered=true&contentType=compilation";
    public static final String PAGINATION_REVIEW = "/do/reviews/next_page?filtered=true&contentType=review";

    //servlet forwarding
    public static final String FORWARD_SERVLET_UPLOAD_ERROR = "/do/admin/upload_error";
    public static final String FORWARD_SERVLET_CREATE_SONG = "/do/admin/create_song";



    private PagePath() {
    }
}
