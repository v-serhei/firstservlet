package by.verbitsky.servletdemo.controller.command;

public class PagePath {

    //redirect path
    public static final String REDIRECT_MAIN_PAGE = "/audiobox/do/main";
    public static final String REDIRECT_REVIEW_PAGE = "/audiobox/do/reviews";
    public static final String REDIRECT_LOGIN_PAGE = "/audiobox/do/login";
    public static final String REDIRECT_ORDER_PAGE = "/audiobox/do/order";
    public static final String REDIRECT_PROFILE_PAGE = "/audiobox/do/profile";
    public static final String REDIRECT_ORDER_PAYMENT_RESULT_PAGE = "/audiobox/do/payment/operation_result";
    public static final String REDIRECT_ORDER_CREATION_RESULT_PAGE = "/audiobox/do/create_order/operation_result";

    //public static final String REDIRECT_COMPILATION_PAGE = "/do/compilations";
    //public static final String REDIRECT_ADMIN_PAGE = "/do/admin";



    //path to jsp (for forwarding)
    public static final String FORWARD_MAIN_PAGE = "/pages/mainpage.jsp";
    public static final String FORWARD_RESULT_PAGE = "/pages/user/result.jsp";
    public static final String FORWARD_REVIEW_PAGE = "/pages/reviews.jsp";
    public static final String FORWARD_COMPILATION_PAGE = "/pages/compilations.jsp";
    public static final String FORWARD_LOGIN_PAGE = "/pages/login.jsp";
    public static final String FORWARD_ORDER_PAGE = "/pages/user/order.jsp";
    public static final String FORWARD_REGISTRATION_PAGE = "/pages/registration.jsp";
    public static final String FORWARD_ADMIN_PAGE = "/pages/admin/adminpage.jsp";
    public static final String FORWARD_PROFILE_PAGE = "/pages/user/profile.jsp";
    public static final String FORWARD_ERROR_PAGE = "/pages/error/errorPage.jsp";

    //pagination controls href attribute value
    public static final String PAGINATION_MAIN = "/do/main/next_page?filtered=true&contentType=song";
    public static final String PAGINATION_COMPILATION = "/do/compilations/next_page?filtered=true&contentType=compilation";
    public static final String PAGINATION_REVIEW = "/do/reviews/next_page?filtered=true&contentType=review";

    private PagePath() {
    }
}
