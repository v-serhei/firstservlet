package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.exception.CommandException;

public class UploadErrorPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {


        return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
    }
}