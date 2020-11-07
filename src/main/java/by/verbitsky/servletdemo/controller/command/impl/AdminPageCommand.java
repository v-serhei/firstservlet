package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class AdminPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        if (content == null) {
            throw new CommandException("AdminPageCommand: received null content");
        }
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);

        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотреть что нужно подгрузить на страницу и сделать запрос в БД
            if (CommandPermissionValidator.isUserHasPermission(user, this)) {
                result = new CommandResult(PagePath.FORWARD_ADMIN_PAGE, false);
            } else {
                result = new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
            }
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}