package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasketAddCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        String lastPage = content.getRequestParameter(ParameterName.PAGE_MARK);
        if (lastPage == null || lastPage.isEmpty()) {
            lastPage = PagePath.MAIN_PAGE_REDIRECT;
        }
        CommandResult result;
        if (user.getLoginStatus()) {
            Long songId = Long.parseLong(content.getRequestParameter(ParameterName.ORDERED_SONG_ID));
            user.getBasket().addSong(songId);
            result = new CommandResult(lastPage, true);
        }else {
            result = new CommandResult(PagePath.LOGIN_PAGE, true);
        }
        return result;
    }
}
