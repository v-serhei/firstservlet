package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;
import by.verbitsky.servletdemo.util.CommandPermissionValidator;

public class AdminPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        User user = (User) content.getSessionAttribute(AttributesNames.SESSION_ATTR_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            //todo посмотрет ьчто нужно подгрузить на страницу и сделать запрос в БД
            if (CommandPermissionValidator.isUserHasPermission(user, this)) {
                result = new CommandResult(ProjectPages.REDIRECT_ADMIN_PAGE, true);
            }else {
                //todo в аттрибут причину переадресации - не хватает прав!
                result = new CommandResult(ProjectPages.REDIRECT_ERROR_PAGE, true);
            }
        } else {
            result = new CommandResult(ProjectPages.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
