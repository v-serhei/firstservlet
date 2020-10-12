package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class LoginPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        //todo проверить на то, залогинен ли
        CommandResult result = new CommandResult(ProjectPages.LOGIN_PAGE, true);
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, this);
        return result;
    }
}
