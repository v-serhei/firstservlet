package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.common.LogoutCommand;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.util.Optional;

public class AdminUpdateUserCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        try {
            String userName = content.getRequestParameter(ParameterName.USER_NAME);
            int userDiscount = Integer.parseInt(content.getRequestParameter(ParameterName.USER_DISCOUNT));
            String userRole = content.getRequestParameter(ParameterName.USER_ROLE);
            String userStatus = content.getRequestParameter(ParameterName.USER_STATUS);
            Optional<User> selectedUser = UserServiceImpl.INSTANCE.findUserByName(userName);
            if (selectedUser.isPresent()){
                boolean updateResult = executeUpdate(content, userDiscount, userRole, userStatus, selectedUser.get());
                if (updateResult) {
                    if (selectedUser.get().getBlockedStatus() && selectedUser.get().equals(user)) {
                        return new LogoutCommand().execute(content);
                    }
                }
            }else {
                content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_USER_UPDATE_NOT_FOUND);
            }
        }catch (NumberFormatException ex) {
            throw new CommandException("UpdateUserCommand: wrong user parameters for update", ex);
        } catch (ServiceException e) {
            throw new CommandException("UpdateUserCommand: error while searching user", e);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_USER_MANAGEMENT_PAGE, true);
    }

    private boolean executeUpdate(SessionRequestContent content, int userDiscount, String userRole, String userStatus, User selectedUser) throws ServiceException {
        selectedUser.setDiscount(userDiscount);
        selectedUser.setRoleId(UserServiceImpl.INSTANCE.getRoleIdByDescription(userRole));
        selectedUser.setBlockedStatus(UserServiceImpl.INSTANCE.getBlockedStatusIdByDescription(userStatus));
        boolean updateResult = UserServiceImpl.INSTANCE.updateUser(selectedUser);
        if (updateResult) {
            content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_USER_UPDATE_SUCCESSFUL);
        }else {
            content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_USER_UPDATE_SQL_ERROR);
        }
        return updateResult;
    }
}
