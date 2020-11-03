package by.verbitsky.servletdemo.controller.filter;

import by.verbitsky.servletdemo.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebFilter
public class SessionAttributeCleanFilter implements Filter {
    private static final String DEFAULT_PAGE = "default";
    public static final String LANG_SWITCH = "langswitch";
    private List<String> urlPatterns;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String prevUri = (String) ((HttpServletRequest) req).getSession().getAttribute(AttributeName.SESSION_USER_LAST_QUERY);
        String requestedUri = ((HttpServletRequest) req).getRequestURI();
        String prevPage = definePage(prevUri);
        String requestedPage = definePage(requestedUri);
        if (!prevPage.equals(requestedPage)) {
            if (!requestedPage.equals(LANG_SWITCH)) {
                HttpSession session = ((HttpServletRequest) req).getSession();
                removeExcessAttributes(session);
            }
        }
        chain.doFilter(req, resp);
    }

    private void removeExcessAttributes(HttpSession session) {
        /*
        session.removeAttribute(AttributeName.CONTENT_CURRENT_PAGE_NUMBER);
        session.removeAttribute(AttributeName.CONTENT_TOTAL_PAGE_COUNT);
        session.removeAttribute(AttributeName.CONTENT);
        session.removeAttribute(AttributeName.ADDITIONAL_CONTENT_SONGS);
        session.removeAttribute(AttributeName.SONG_FILTER);
        session.removeAttribute(AttributeName.PAGINATION_CONTROLS_LINK);
        session.removeAttribute(AttributeName.SINGER_LIST);
        session.removeAttribute(AttributeName.GENRE_LIST);
        session.removeAttribute(AttributeName.SEARCH_COUNT_RESULT);*/
    }

    private String definePage(String prevUri) {
        if (prevUri == null) {
            return DEFAULT_PAGE;
        }
        Optional<String> result = urlPatterns.stream()
                .filter(prevUri::contains)
                .findFirst();
        return result.orElse(DEFAULT_PAGE);
    }

    public void init(FilterConfig config) throws ServletException {
        urlPatterns = new ArrayList<>();
        urlPatterns.add("mainpage");
        urlPatterns.add("langswitch");
        urlPatterns.add("login");
        urlPatterns.add("registration");
        urlPatterns.add("register");
        urlPatterns.add("logout");
        urlPatterns.add("profile");
        urlPatterns.add("admin");
        urlPatterns.add("review");
        urlPatterns.add("album");
    }

}
