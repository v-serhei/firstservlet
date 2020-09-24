package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.controller.SessionRequestContent;

public class ChangeLanguageCommand implements Command {
    @Override
    public void execute(SessionRequestContent request) {
       //UserService.INSTANCE.processLanguageSwitch(request);
    }
}
