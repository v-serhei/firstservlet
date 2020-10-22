package by.verbitsky.servletdemo.controller.filter;

import javax.servlet.*;
import java.io.IOException;


public class EncodingFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //todo проверка на существование (см занятие)
        servletRequest.setCharacterEncoding(DEFAULT_ENCODING);
        servletResponse.setCharacterEncoding(DEFAULT_ENCODING);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}