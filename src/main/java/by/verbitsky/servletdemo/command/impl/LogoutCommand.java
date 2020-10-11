package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.service.impl.UserService;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) {
        UserService.INSTANCE.processLogout(content);
        return null;
    }
}
