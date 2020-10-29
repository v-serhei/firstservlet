package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;

public class BasketRemoveCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user.getLoginStatus()) {
            Long songId = Long.parseLong(content.getRequestParameter(ParameterName.ORDERED_SONG_ID));
            user.getBasket().removeSong(songId);
            result = new CommandResult((String) content.getSessionAttribute(AttributeName.SESSION_LAST_URI), true);
        }else {
            result = new CommandResult(PagePath.LOGIN_PAGE, true);
        }
        return result;
    }
}
