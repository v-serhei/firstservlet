package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.OrderFactory;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.entity.impl.OrderFactoryImpl;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

import java.util.Set;

public class CreateOrderCommand implements Command {
    private OrderFactory<Order> factory = new OrderFactoryImpl();

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user.getLoginStatus()) {
            if (CommandPermissionValidator.isUserHasPermission(user, this)) {
                Set<Song> songs = user.getBasket().getSongs();
                if (songs.size() > 0) {
                    Order order = factory.createOrder(user, songs);
                    try {
                        OrderServiceImpl.INSTANCE.addOrder(order);
                        user.getBasket().clear();
                    } catch (ServiceException e) {
                        throw new CommandException("CreateOrderCommand: Error while creating order", e);
                    }
                    content.addSessionAttribute(AttributeName.SESSION_CURRENT_ORDER, order);
                    result = new CommandResult(PagePath.REDIRECT_ORDER_PAGE, true);
                } else {
                    result = addAttributesForErrorPage(content);

                }
            } else {
                result = new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
            }
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }

    private CommandResult addAttributesForErrorPage(SessionRequestContent content) {
        content.addRequestAttribute(AttributeName.OPERATION_TYPE, AttributeValue.CREATE_ORDER);
        content.addRequestAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
        content.addRequestAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.EMPTY_ORDER_LIST);
        content.addRequestAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_BACK);
        content.addRequestAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_MAIN_PAGE);
        return new CommandResult(PagePath.FORWARD_RESULT_PAGE, false);
    }
}