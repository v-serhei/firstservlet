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

    //path to jsp (for forwarding)
    public static final String FORWARD_MAIN_PAGE = "/pages/common/mainpage.jsp";
    public static final String FORWARD_RESULT_PAGE = "/pages/user/result.jsp";
    public static final String FORWARD_REVIEW_PAGE = "/pages/common/reviews.jsp";
    public static final String FORWARD_COMPILATION_PAGE = "/pages/common/compilations.jsp";
    public static final String FORWARD_LOGIN_PAGE = "/pages/common/login.jsp";
    public static final String FORWARD_ORDER_PAGE = "/pages/user/order.jsp";
    public static final String FORWARD_REGISTRATION_PAGE = "/pages/common/registration.jsp";
    public static final String FORWARD_ADMIN_USER_MANAGEMENT = "/pages/admin/usermanagement.jsp";
    public static final String FORWARD_USER_SETTINGS_PAGE = "/pages/user/profilesettings.jsp";
    public static final String FORWARD_USER_ORDERS_PAGE = "/pages/user/profileorders.jsp";
    public static final String FORWARD_USER_REVIEWS_PAGE = "/pages/user/profilereviews.jsp";
    public static final String FORWARD_ADD_REVIEW_PAGE = "/pages/common/addreview.jsp";
    public static final String FORWARD_ERROR_PAGE = "/pages/error/errorPage.jsp";

    //pagination controls href attribute value
    public static final String PAGINATION_MAIN = "/do/main/next_page?filtered=true&contentType=song";
    public static final String PAGINATION_COMPILATION = "/do/compilations/next_page?filtered=true&contentType=compilation";
    public static final String PAGINATION_REVIEW = "/do/reviews/next_page?filtered=true&contentType=review";

    private PagePath() {
    }
}
