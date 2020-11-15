package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

import java.util.Optional;

public class PayOrderCommand implements Command {
    private OrderServiceImpl orderService = OrderServiceImpl.INSTANCE;

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
        long orderId;
        try {
            orderId = Long.parseLong(content.getRequestParameter(ParameterName.ORDER_ID));
            Optional<Order> paidOrder = orderService.findOrderById(orderId);
            if (paidOrder.isPresent()) {
                if (paidOrder.get().getOrderStatusId() != (orderService.getPaidOrderStatusId())) {
                    paidOrder.get().setOrderStatus(orderService.getPaidOrderStatusId());
                    boolean isUpdated = orderService.updateOrder(paidOrder.get(), user);
                    if (isUpdated) {
                        content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.PAY_ORDER);
                        content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_COMPLETED);
                        content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.ORDER_PAYMENT_SUCCESSFUL);
                        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_PROFILE);
                        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_ORDER_PAGE);
                        result = new CommandResult(PagePath.REDIRECT_ORDER_PAYMENT_RESULT_PAGE, true);
                    } else {
                        throw new CommandException("PayOrderCommand: error while updating order status");
                    }
                } else {
                    content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.PAY_ORDER);
                    content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                    content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.ORDER_ALREADY_PAID);
                    content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_PROFILE);
                    content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_ORDER_PAGE);
                    result = new CommandResult(PagePath.REDIRECT_ORDER_PAYMENT_RESULT_PAGE, true);
                }
            } else {
                throw new CommandException("PayOrderCommand: error while receiving order from db");
            }
        } catch (NumberFormatException ex) {
            throw new CommandException("PayOrderCommand: wrong parameter orderId", ex);
        } catch (ServiceException e) {
            throw new CommandException("PayOrderCommand: error while receiving order from db", e);
        }
        return result;
    }
}