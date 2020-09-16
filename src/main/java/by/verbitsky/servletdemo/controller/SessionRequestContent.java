package by.verbitsky.servletdemo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

class SessionRequestContent {
    private final HashMap<String, Object> requestAttributes;
    private final HashMap<String, String> requestParameters;
    private final HashMap<String, Object> sessionAttributes;

    public SessionRequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
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

    public void extractAllParametersFromRequest(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            requestAttributes.put(paramName, request.getParameter(paramName));
        }
    }

    public void pushAttributesToRequest(HttpServletRequest request) {
        requestAttributes.forEach((request::setAttribute));
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

}
