package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class OperationResultCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user.getLoginStatus()) {
            result = new CommandResult(PagePath.FORWARD_RESULT_PAGE, false);
        } else {
            result = new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
        }
        return result;
    }
}
