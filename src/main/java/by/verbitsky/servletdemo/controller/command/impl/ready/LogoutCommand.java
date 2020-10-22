package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.PagePaths;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        User user = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            content.getSession().invalidate();
        }else {
            throw new CommandExecutionException("LogoutCommand: attribute user is null");
        }
        result = new CommandResult(PagePaths.MAIN_PAGE, true);
        return result;
    }
}