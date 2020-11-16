package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class UserOrdersCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        } else {
            List<Order> orderList;
            try {
                orderList = OrderServiceImpl.INSTANCE.findUserOrders(user.getUserId());
            } catch (ServiceException e) {
                throw new CommandException("UserOrdersCommand: error while searching user orders", e);
            }
            long totalCount = orderList.size();
            long totalPaidCount = orderList.stream().filter(Order::isOrderPaid).count();
            BigDecimal totalPaidSum = BigDecimal.ZERO;
            for (Order order : orderList) {
                if (order.isOrderPaid()) {
                    totalPaidSum = totalPaidSum.add(order.getOrderPrice());
                }
            }
            content.addRequestAttribute(AttributeName.ORDER_LIST, orderList);
            content.addRequestAttribute(AttributeName.ORDER_TOTAL_COUNT, totalCount);
            content.addRequestAttribute(AttributeName.ORDER_TOTAL_PAID_COUNT, totalPaidCount);
            content.addRequestAttribute(AttributeName.ORDER_TOTAL_PRICE, totalPaidSum);
            return new CommandResult(PagePath.FORWARD_USER_ORDERS_PAGE, false);
        }
    }
}