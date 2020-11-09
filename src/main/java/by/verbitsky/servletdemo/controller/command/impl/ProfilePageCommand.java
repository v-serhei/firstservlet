package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.PagePath;

public class ProfilePageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотрет ьчто нужно подгрузить на страницу и сделать запрос в БД
            result = new CommandResult(PagePath.FORWARD_USER_SETTINGS_PAGE, false);
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
