package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.AdminCommand;
import by.verbitsky.servletdemo.entity.Order;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.OrderServiceImpl;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserManagementPageCommand extends AdminCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        clearOperationMessageAttributes(content);
        String selectedUserName = content.getRequestParameter(ParameterName.SELECTED_USER);
        if (selectedUserName != null && !selectedUserName.isEmpty()) {
            try {
                Optional<User> selectedUser = UserServiceImpl.INSTANCE.findUserByName(selectedUserName);
                if (selectedUser.isPresent()) {
                    addInfoToRequest(content, selectedUser.get());
                } else {
                    content.addRequestAttribute(AttributeName.ADMIN_USER_ENABLE, false);
                }
            } catch (ServiceException e) {
                throw new CommandException("UserManagementPageCommand: error while searching selected user details", e);
            }
        } else {
            content.addRequestAttribute(AttributeName.ADMIN_USER_ENABLE, false);
        }
        try {
            List<User> userList = UserServiceImpl.INSTANCE.findAllUsers();
            content.addRequestAttribute(AttributeName.ADMIN_USER_LIST, userList);
        } catch (ServiceException e) {
            throw new CommandException("UserManagementPageCommand: error while searching all users", e);
        }
        return new CommandResult(PagePath.FORWARD_ADMIN_USER_MANAGEMENT, false);
    }

    private void addInfoToRequest(SessionRequestContent content, User selectedUser) throws ServiceException {
        List<Order> userOrderList = OrderServiceImpl.INSTANCE.findUserOrders(selectedUser.getUserId());
        long totalPaidCount = userOrderList.stream().filter(Order::isOrderPaid).count();
        BigDecimal totalPaidSum = BigDecimal.ZERO;
        for (Order order : userOrderList) {
            if (order.isOrderPaid()) {
                totalPaidSum = totalPaidSum.add(order.getOrderPrice());
            }
        }
        List<String> roleList = UserServiceImpl.INSTANCE.getRoleList();
        content.addRequestAttribute(AttributeName.ADMIN_USER_ENABLE, true);
        content.addRequestAttribute(AttributeName.ADMIN_SELECTED_USER, selectedUser);
        content.addRequestAttribute(AttributeName.ORDER_TOTAL_PAID_COUNT, totalPaidCount);
        content.addRequestAttribute(AttributeName.ORDER_TOTAL_PRICE, totalPaidSum);
        content.addRequestAttribute(AttributeName.ADMIN_ROLE_LIST, roleList);
    }
}