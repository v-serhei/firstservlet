package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class EmptyCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {

        CommandResult result = new CommandResult(ProjectPages.REDIRECT_ERROR_PAGE, true);
        return result;
    }
}
