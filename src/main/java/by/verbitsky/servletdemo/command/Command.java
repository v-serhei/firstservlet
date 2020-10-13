package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;

public interface Command {
    //todo сделать чтобы кидала ошибки и переадресовывать на страницу ошибок
    CommandResult execute(SessionRequestContent content);
}
