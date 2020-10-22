package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;

public class AdminPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        if (content == null) {
            throw new CommandExecutionException("AdminPageCommand: received null content");
        }
        User user;
        try {
            user = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        } catch (ClassCastException e) {
            throw new CommandExecutionException("AdminPageCommand: received class cast exception while getting attribute \"User\"");
        }

        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотреть что нужно подгрузить на страницу и сделать запрос в БД
            if (CommandPermissionValidator.isUserHasPermission(user, this)) {
                result = new CommandResult(PagePaths.ADMIN_PAGE, true);
            } else {
                content.addSessionAttribute(AttributeNames.REQUEST_ATTR_COMMAND_ERROR_MESSAGE,
                        AttributeNames.REQUEST_ATTR_ADMIN_PAGE_ACCESS_DENIED);
                content.addSessionAttribute(AttributeNames.REQUEST_ATTR_REQUESTED_URL,
                        content.getRequest().getRequestURI());
                result = new CommandResult(PagePaths.ERROR_PAGE, true);
            }
        } else {
            result = new CommandResult(PagePaths.LOGIN_PAGE, true);
        }
        return result;
    }
}
