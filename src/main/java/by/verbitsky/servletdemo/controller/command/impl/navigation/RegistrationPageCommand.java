package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class RegistrationPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        CommandResult result;
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (user != null) {
            if (!user.getLoginStatus()) {
                result = new CommandResult(PagePath.FORWARD_REGISTRATION_PAGE, false);
            } else {
                result = new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
            }
        } else {
            throw new CommandException("RegistrationPageCommand: attribute \"User\" is null");
        }
        return result;
    }
}