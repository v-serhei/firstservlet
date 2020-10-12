package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

public interface Command {
    CommandResult execute(SessionRequestContent content);
}
