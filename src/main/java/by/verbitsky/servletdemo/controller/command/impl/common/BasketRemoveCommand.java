package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class BasketRemoveCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        String lastPage = content.getRequestParameter(ParameterName.PAGE_MARK);
        if (lastPage == null || lastPage.isEmpty()) {
            lastPage = PagePath.REDIRECT_MAIN_PAGE;
        }
        CommandResult result;
        if (user.getLoginStatus()) {
            Long songId = Long.parseLong(content.getRequestParameter(ParameterName.SONG_ID));
            user.getBasket().removeSong(songId);
            result = new CommandResult(lastPage, true);
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
