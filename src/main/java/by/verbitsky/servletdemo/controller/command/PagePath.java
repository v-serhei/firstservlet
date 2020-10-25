package by.verbitsky.servletdemo.controller.command;

public class PagePath {
    public static final String MAIN_PAGE = "/pages/mainpage.jsp";
    public static final String LOGIN_PAGE = "/pages/login.jsp";
    public static final String REGISTRATION_PAGE = "/pages/registration.jsp";
    public static final String ADMIN_PAGE = "/pages/admin/adminpage.jsp";
    public static final String PROFILE_PAGE = "/pages/user/profile.jsp";
    public static final String ERROR_PAGE = "/pages/error/errorPage.jsp";

    public static final String PAGINATION_MAIN = "/mainpage?action=next_song_page&filtered=true";

    private PagePath() {
    }
}
