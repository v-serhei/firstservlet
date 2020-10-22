package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.PagePaths;

public class ProfilePageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотрет ьчто нужно подгрузить на страницу и сделать запрос в БД
            result = new CommandResult(PagePaths.PROFILE_PAGE, true);
        } else {
            //todo дописать причину редиректа
            result = new CommandResult(PagePaths.LOGIN_PAGE, true);
        }
        return result;
    }
}
