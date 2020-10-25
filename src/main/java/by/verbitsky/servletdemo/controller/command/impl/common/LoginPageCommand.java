package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.User;

public class LoginPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (!user.getLoginStatus()) {
            result = new CommandResult(PagePath.LOGIN_PAGE, true);
        } else {
            result = new CommandResult(PagePath.MAIN_PAGE, true);
        }
        //todo сбрасывать фильтр
        return result;
    }
}