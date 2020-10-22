package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeNames;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePaths;
import by.verbitsky.servletdemo.controller.command.AttributeValues;

public class EmptyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        content.addSessionAttribute(AttributeNames.REQUEST_ATTR_COMMAND_ERROR_MESSAGE,
                AttributeValues.EMPTY_COMMAND_ERROR_MESSAGE);
        content.addSessionAttribute(AttributeNames.REQUEST_ATTR_REQUESTED_URL,
                content.getRequest().getRequestURI());
        return new CommandResult(PagePaths.ERROR_PAGE, true);
    }
}
