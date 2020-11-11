package by.verbitsky.servletdemo.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter
public class EncodingFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(DEFAULT_ENCODING);
        servletResponse.setCharacterEncoding(DEFAULT_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}