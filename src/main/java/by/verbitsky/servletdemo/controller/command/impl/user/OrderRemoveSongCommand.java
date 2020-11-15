package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.navigation.OrderPageCommand;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

import java.util.Optional;

public class OrderRemoveSongCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)){
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        CommandResult result;
        long songId;
        long orderId;
        try {
            orderId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_ID));
            songId = Long.parseLong(content.getRequestParameter(ParameterName.ORDERED_SONG_ID));
            Optional<Order> order = OrderServiceImpl.INSTANCE.findOrderById(orderId);
            if (order.isPresent()) {
                OrderServiceImpl.INSTANCE.removeSongFromOrder(order.get(), songId, user);
                if (order.get().getOrderList().size() > 0) {
                    content.addSessionAttribute(AttributeName.SESSION_CURRENT_ORDER, order.get());
                    result = new OrderPageCommand().execute(content);
                } else {
                    //if orderList is empty after removing song - delete current order
                    boolean delResult = OrderServiceImpl.INSTANCE.deleteOrder(user, order.get().getOrderId());
                    if(delResult) {
                        result = new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
                    }else {
                        result = addAttributesForErrorPage(content);
                    }
                }
            } else {
                result = addAttributesForErrorPage(content);
            }
        } catch (NumberFormatException ex) {
            result = addAttributesForErrorPage(content);
        } catch (ServiceException e) {
            throw new CommandException("OrderRemoveSongCommand: error while getting order from db");
        }
        return result;
    }

    private CommandResult addAttributesForErrorPage(SessionRequestContent content) {
        content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.REMOVE_SONG_FROM_ORDER);
        content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
        content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.ORDER_NOT_EXIST);
        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_BACK);
        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_MAIN_PAGE);
        return new CommandResult(PagePath.FORWARD_RESULT_PAGE, false);
    }
}
