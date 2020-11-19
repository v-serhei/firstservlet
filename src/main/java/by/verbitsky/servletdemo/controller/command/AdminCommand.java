package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

/**
 * Abstract admin command. This class contains method which used in admin commands
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public abstract class AdminCommand {

    /**
     *
     *  Moves attributes from Session to Request
     *  These attributes contain admin command's result: positive or negative, and
     *  message key which used in jsp for displaying operation result message
     *
     * @see Command
     * @see SessionRequestContent
     *
     */
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
