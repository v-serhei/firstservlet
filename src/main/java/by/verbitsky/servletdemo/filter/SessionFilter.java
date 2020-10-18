package by.verbitsky.servletdemo.filter;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.projectconst.AttributesNames;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

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
                session.setAttribute(AttributesNames.SESSION_ATTR_LOCALE, Locale.getDefault());
                session.setAttribute(AttributesNames.SESSION_ATTR_USER, user);
            }
        }
    }
}
