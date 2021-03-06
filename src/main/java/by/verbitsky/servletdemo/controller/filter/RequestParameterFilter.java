package by.verbitsky.servletdemo.controller.filter;

import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.ParameterName;
import by.verbitsky.servletdemo.util.FieldDataValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


/**
 * Request parameter filter. Checks user inputs for forbidden characters
 * If incoming parameter doesn't pass validation - request will not be processed and user will be redirected
 * to error page
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see FieldDataValidator
 * @see HttpServletRequest
 * @see <a href=https://en.wikipedia.org/wiki/Character_encoding> Character encoding </a>
 */

@WebFilter
public class RequestParameterFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Enumeration<String> parameterNames = request.getParameterNames();
        boolean parameterCheck = true;
        while (parameterNames.hasMoreElements() & parameterCheck) {
            String currentParameterName = parameterNames.nextElement();
            String currentParameter = request.getParameter(currentParameterName);
            if (ParameterName.REVIEW_TEXT.equalsIgnoreCase(currentParameterName)) {
                parameterCheck = FieldDataValidator.validateRequestTextAreaParameter(currentParameter);
            } else {
                parameterCheck = FieldDataValidator.validateRequestParameter(currentParameter);
            }
        }
        if (!parameterCheck) {
            request.getSession().setAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeName.PARAMETER_VALIDATION_ERROR_MESSAGE);
            request.getSession().setAttribute(AttributeName.REQUESTED_URL, request.getRequestURI());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        chain.doFilter(req, resp);
    }
}