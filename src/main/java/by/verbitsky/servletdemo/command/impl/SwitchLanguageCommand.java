package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.service.impl.UserService;

public class SwitchLanguageCommand implements Command {
    @Override
    public void execute(SessionRequestContent request) {
       UserService.INSTANCE.processLanguageSwitch(request);
    }
}
