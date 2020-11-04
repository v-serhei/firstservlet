package by.verbitsky.servletdemo.controller.filter;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter
public class SessionFilter implements Filter {
    private static final int DEFAULT_SESSION_LIVE_TIME = 3600;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        processNewSession(session);
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
}
