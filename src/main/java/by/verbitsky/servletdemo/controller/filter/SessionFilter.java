package by.verbitsky.servletdemo.controller.filter;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;


/**
 * Session filter. Checks current user session.
 * If session doesn't exist (first user request) - it will be created and User object will be added to Session attributes.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see HttpSession
 */

@WebFilter
public class SessionFilter implements Filter {
    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    private static final String URL_PARAMETER_PREFIX = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(true);
        processNewSession(session);
        session.setAttribute(AttributeName.SESSION_USER_LAST_QUERY, constructQuery(request));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void processNewSession(HttpSession session) {
        if (session != null) {
            if (session.isNew()) {
                session.setMaxInactiveInterval(DEFAULT_SESSION_LIVE_TIME);
                User user = new User();
                user.setLoginStatus(false);
                session.setAttribute(AttributeName.SESSION_LOCALE, Locale.getDefault());
                session.setAttribute(AttributeName.SESSION_USER, user);
            }
        }
    }

    private String constructQuery(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        StringBuilder sb = new StringBuilder(uri);
        if (query != null && !query.isEmpty()) {
            sb.append(URL_PARAMETER_PREFIX);
            sb.append(query);
        }
        return sb.toString();
    }
}
