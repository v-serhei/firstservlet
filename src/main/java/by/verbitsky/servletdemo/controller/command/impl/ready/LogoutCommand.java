package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.PagePath;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            content.getSession().invalidate();
        }else {
            throw new CommandException("LogoutCommand: attribute \"User\" is null\"");
        }
        result = new CommandResult(PagePath.LOGOUT_PAGE, true);
        return result;
    }
}