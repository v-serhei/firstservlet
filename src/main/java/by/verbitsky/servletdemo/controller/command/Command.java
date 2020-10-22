package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.exception.CommandExecutionException;

public interface Command {
    CommandResult execute(SessionRequestContent content) throws CommandExecutionException;
}
