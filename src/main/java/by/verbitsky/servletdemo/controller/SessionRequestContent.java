package by.verbitsky.servletdemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Session request content. Object of SessionRequestContent type contains all attributes and request parameters
 * that are needed to process user command and redirect user to result page
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see HttpServletRequest
 * @see javax.servlet.http.HttpServletResponse
 */
public class SessionRequestContent {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String> requestParameters;
    private final HashMap<String, Object> sessionAttributes;
    private final HttpSession session;
    private final HttpServletRequest request;

    /**
     * Constructor. Instantiates a new Session request content.
     *
     * @param request - required to get Session object, session and request attributes and request parameters
     */
    public SessionRequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractParametersFromRequest(request);
        extractAttributesFromRequest(request);
        extractAttributesFromSession(request);
        session = request.getSession(false);
        this.request = request;
    }

    public void addSessionAttribute(String attributeName, Object value) {
        if (attributeName != null && !attributeName.isEmpty()) {
            if (value != null) {
                sessionAttributes.put(attributeName, value);
            }
        }
    }

    public void addRequestAttribute(String attributeName, Object value) {
        if (attributeName != null && !attributeName.isEmpty()) {
            if (value != null) {
                requestAttributes.put(attributeName, value);
            }
        }
    }

    public void extractAttributesFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attName = attributeNames.nextElement();
            sessionAttributes.put(attName, session.getAttribute(attName));
        }
    }

    public void extractAttributesFromRequest(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attName = attributeNames.nextElement();
            requestAttributes.put(attName, request.getAttribute(attName));
        }
    }

    public void extractParametersFromRequest(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            requestParameters.put(paramName, request.getParameter(paramName));
        }
    }

    /**
     * Writes all request attributes from requestAttributes to request object
     */
    public void pushAttributesToRequest() {
        requestAttributes.forEach(request::setAttribute);
    }

    /**
     * Writes all session attributes from sessionAttributes to session object
     */
    public void pushAttributesToSession() {
        sessionAttributes.forEach(session::setAttribute);
    }

    public String getRequestParameter(String parameterName) {
        return requestParameters.get(parameterName);
    }

    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public Object getRequestAttribute(String attributeName) {
        return requestAttributes.get(attributeName);
    }

    public void removeSessionAttribute(String attributeName) {
        sessionAttributes.remove(attributeName);
        session.removeAttribute(attributeName);
    }

    public HttpSession getSession() {
        return session;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
