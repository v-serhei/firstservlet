package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.service.impl.UserService;

public class RegisterCommand implements Command {
    @Override
    public void execute(SessionRequestContent content) {
       UserService.INSTANCE.processRegistration(content);
    }
}
