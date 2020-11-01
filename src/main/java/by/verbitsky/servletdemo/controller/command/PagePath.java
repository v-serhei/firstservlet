package by.verbitsky.servletdemo.controller.command;

public class PagePath {
    public static final String MAIN_PAGE_REDIRECT = "/mainpage?action=main_page";
    public static final String MAIN_PAGE = "/pages/mainpage.jsp";
    public static final String REVIEW_PAGE = "/pages/reviews.jsp";
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String REGISTRATION_PAGE = "/pages/registration.jsp";
    public static final String ADMIN_PAGE = "/pages/admin/adminpage.jsp";
    public static final String PROFILE_PAGE = "/pages/user/profile.jsp";
    public static final String ERROR_PAGE = "/pages/error/errorPage.jsp";

    public static final String PAGINATION_MAIN = "/mainpage?action=next_page&filtered=true&contentType=song";
    public static final String PAGINATION_REVIEW = "/review?action=next_page&filtered=true&contentType=review";

    private PagePath() {
    }
}
