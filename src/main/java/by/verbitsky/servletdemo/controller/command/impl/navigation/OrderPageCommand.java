package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

public class OrderPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        Order currentOrder = (Order) content.getSessionAttribute(AttributeName.SESSION_CURRENT_ORDER);
        CommandResult result;
        if (user.getLoginStatus()) {
            if (currentOrder.getUserId() == user.getUserId() ||
                    user.getUserId() == UserServiceImpl.INSTANCE.getAdminRoleId()) {
                content.addRequestAttribute(AttributeName.ORDER, currentOrder);
                content.getSession().removeAttribute(AttributeName.SESSION_CURRENT_ORDER);
                //show button "Pay order" if order is not paid and user role != Admin
                if (!currentOrder.isOrderPaid() || !(user.getUserId() == UserServiceImpl.INSTANCE.getAdminRoleId())) {
                    content.addRequestAttribute(AttributeName.SHOW_ORDER_CONTROLS, true);
                }else {
                    content.addRequestAttribute(AttributeName.SHOW_ORDER_CONTROLS, false);
                }
                result = new CommandResult(PagePath.FORWARD_ORDER_PAGE, false);
            }else {
                result = new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
            }
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
