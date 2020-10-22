package by.verbitsky.servletdemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

public class SessionRequestContent {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String> requestParameters;
    private final HashMap<String, Object> sessionAttributes;
    private final HttpSession session;
    private final HttpServletRequest request;

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

    public void pushAttributesToRequest() {
        requestAttributes.forEach(request::setAttribute);
    }

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

    public HttpSession getSession() {
        return session;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
