package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class SongManagementPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        CommandResult result;

        //todo посмотреть что нужно подгрузить на страницу и сделать запрос в БД
        if (CommandPermissionValidator.isUserHasPermission(user, this)) {
            result = new CommandResult(PagePath.FORWARD_ADMIN_SONG_MANAGEMENT, false);
        } else {
            result = new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }

        return result;
    }
}