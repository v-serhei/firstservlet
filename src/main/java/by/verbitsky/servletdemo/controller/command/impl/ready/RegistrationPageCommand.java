package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePaths;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;

public class RegistrationPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        CommandResult result;
        User user = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        if (user != null) {
            if (!user.getLoginStatus()) {
                result = new CommandResult(PagePaths.REGISTRATION_PAGE, true);
            } else {
                result = new CommandResult(PagePaths.MAIN_PAGE, true);
            }
        } else {
            throw new CommandExecutionException("RegistrationPageCommand: attribute \"User\" is null");
        }
        return result;
    }
}