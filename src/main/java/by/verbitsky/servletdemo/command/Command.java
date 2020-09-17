package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

public interface Command {
    void execute(SessionRequestContent request);
}
