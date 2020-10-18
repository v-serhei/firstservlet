package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class LoginPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        if (content == null) {
            throw new CommandExecutionException("LoginPageCommand: received null content");
        }
        //todo проверить на то, залогинен ли - если нет - редирект на логин, иначе на страницу с которой пришел
        User user = (User) content.getSessionAttribute(AttributesNames.SESSION_ATTR_USER);
        CommandResult result;
        if (!user.getLoginStatus()) {
            result = new CommandResult(ProjectPages.LOGIN_PAGE, true);
        } else {
            //todo подумать
            result = new CommandResult(ProjectPages.MAIN_PAGE, true);
        }
        return result;
    }
}
