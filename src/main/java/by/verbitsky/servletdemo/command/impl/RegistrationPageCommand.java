package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class RegistrationPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        //todo проверить на то, залогинен ли. проверить предыдущую команду!!!
        CommandResult result = new CommandResult(ProjectPages.REDIRECT_REGISTRATION_PAGE, true);
        return result;
    }
}
