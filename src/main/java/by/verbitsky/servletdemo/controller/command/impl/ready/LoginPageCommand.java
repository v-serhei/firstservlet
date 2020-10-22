package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePaths;
import by.verbitsky.servletdemo.entity.User;

public class LoginPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        CommandResult result;
        if (!user.getLoginStatus()) {
            result = new CommandResult(PagePaths.LOGIN_PAGE, true);
        } else {
            result = new CommandResult(PagePaths.MAIN_PAGE, true);
        }
        return result;
    }
}
