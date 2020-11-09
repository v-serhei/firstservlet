package by.verbitsky.servletdemo.controller.command.impl.user;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

public class UpdateEmailCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        String email = content.getRequestParameter(ParameterName.USER_EMAIL);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }else {
            try {
                boolean updateResult = UserServiceImpl.INSTANCE.updateUserEmail(user, email, content);
                content.addSessionAttribute(AttributeName.USER_UPDATE_FAILED, updateResult);
                content.addSessionAttribute(AttributeName.USER_PROFILE_SHOW_MESSAGE, true);
                if (updateResult) {
                    return new CommandResult(PagePath.REDIRECT_SETTINGS_PAGE, true);

                }else {
                    return new CommandResult(PagePath.FORWARD_USER_SETTINGS_PAGE, false);
                }
            } catch (ServiceException e) {
                throw new CommandException("UpdatePasswordCommand: error while updating user password", e);
            }
        }
    }
}
