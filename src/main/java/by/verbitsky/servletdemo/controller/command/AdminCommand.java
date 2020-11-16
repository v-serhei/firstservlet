package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

public abstract class AdminCommand {

    protected void clearOperationMessageAttributes(SessionRequestContent content) {
        Object attribute = content.getSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG);
        if (attribute != null) {
            boolean enableMessage = (boolean) attribute;
            if (enableMessage) {
                content.removeSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG);
                String operationMessage = (String) content.getSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG);
                content.removeSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG);
                content.addRequestAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
                content.addRequestAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, operationMessage);
            }
        }
    }
}
