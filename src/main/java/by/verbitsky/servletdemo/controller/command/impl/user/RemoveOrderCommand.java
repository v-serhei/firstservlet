package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

public class RemoveOrderCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        try {
            long removedOrderId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_ID));
            boolean result = OrderServiceImpl.INSTANCE.deleteOrder(user, removedOrderId);
            if (result) {
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_COMPLETED);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.ORDER_REMOVE_SUCCESSFUL);
            }else {
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.ORDER_REMOVE_FAIL);
            }
            content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.REMOVE_ORDER);
            content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_PROFILE);
            content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_ORDER_PAGE);
            return new CommandResult(PagePath.REDIRECT_ORDER_REMOVE_RESULT_PAGE, true);
        }catch (NumberFormatException ex) {
            throw new CommandException("RemoveOrderCommand: wrong parameter orderId", ex);
        } catch (ServiceException e) {
            throw new CommandException("RemoveOrderCommand: error while removing order from db", e);
        }
    }
}
