package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.controller.SessionRequestContent;

public class EmptyCommand implements Command {
    @Override
    public void execute(SessionRequestContent content) {
        //todo redirect to error page
        //stub
    }
}
