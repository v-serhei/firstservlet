package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributesNames.SESSION_ATTR_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            content.getSession().invalidate();
        }
        result = new CommandResult(ProjectPages.MAIN_PAGE, true);
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, this);
        return result;
    }
}
