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

    public SessionRequestContent(HttpServletRequest request) {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        extractParametersFromRequest(request);
        extractAttributesFromRequest(request);
        extractAttributesFromSession(request);
        session = request.getSession(false);
    }

    public void addSessionAttribute(String attName, Object value) {
        if (attName != null && !attName.isEmpty()) {
            if (value != null) {
                sessionAttributes.put(attName, value);
            }
        }
    }

    public void addRequestAttribute(String attName, Object value) {
        if (attName != null && !attName.isEmpty()) {
            if (value != null) {
                requestAttributes.put(attName, value);
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

    public void pushAttributesToRequest(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
    }

    public void pushAttributesToSession(HttpServletRequest request) {
       sessionAttributes.forEach(session::setAttribute);
    }

    public String getRequestParameter(String parameterName) {
        return requestParameters.get(parameterName);
    }

    public Object getSessionAttribute(String parameterName) {
        return sessionAttributes.get(parameterName);
    }

    public Object getRequestAttribute(String parameterName) {
        return requestAttributes.get(parameterName);
    }

    public HttpSession getSession() {
        return session;
    }
}
