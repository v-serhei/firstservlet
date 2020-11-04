package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.controller.command.AttributeValue;

public class EmptyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        content.addSessionAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeValue.EMPTY_COMMAND_ERROR_MESSAGE);
        content.addSessionAttribute(AttributeName.REQUESTED_URL, content.getRequest().getRequestURI());
        return new CommandResult(PagePath.FORWARD_ERROR_PAGE, true);
    }
}
