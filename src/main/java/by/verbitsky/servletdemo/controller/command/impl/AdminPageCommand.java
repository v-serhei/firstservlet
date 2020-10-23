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
        User user;
        try {
            user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        } catch (ClassCastException e) {
            throw new CommandException("AdminPageCommand: received class cast exception while getting attribute \"User\"");
        }

        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотреть что нужно подгрузить на страницу и сделать запрос в БД
            if (CommandPermissionValidator.isUserHasPermission(user, this)) {
                result = new CommandResult(PagePath.ADMIN_PAGE, true);
            } else {
                content.addSessionAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeName.ADMIN_PAGE_ACCESS_DENIED);
                content.addSessionAttribute(AttributeName.REQUESTED_URL, content.getRequest().getRequestURI());
                result = new CommandResult(PagePath.ERROR_PAGE, true);
            }
        } else {
            result = new CommandResult(PagePath.LOGIN_PAGE, true);
        }
        return result;
    }
}
