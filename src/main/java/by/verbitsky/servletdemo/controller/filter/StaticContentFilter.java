package by.verbitsky.servletdemo.controller.filter;

import by.verbitsky.servletdemo.controller.command.AttributeName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class StaticContentFilter implements Filter {

    private static final String INDEX_JSP = "index.jsp";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (!request.getRequestURI().contains(INDEX_JSP)) {
            request.getSession().setAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeName.ADMIN_PAGE_ACCESS_DENIED);
            request.getSession().setAttribute(AttributeName.REQUESTED_URL, request.getRequestURI());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
