package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.User;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            content.getSession().invalidate();
        }
        result = new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
        return result;
    }
}