package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.Optional;

public class BasketAddCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        String lastPage = content.getRequestParameter(ParameterName.PAGE_MARK);
        if (lastPage == null || lastPage.isEmpty()) {
            lastPage = PagePath.REDIRECT_MAIN_PAGE;
        }
        CommandResult result;
        if (user.getLoginStatus()) {
            long songId = Long.parseLong(content.getRequestParameter(ParameterName.SONG_ID));
            try {
                Optional<AudioContent> song = AudioContentService.INSTANCE.findContentById(ContentType.SONG, songId);
                song.ifPresent(audioContent -> user.getBasket().addSong((Song) audioContent));
                result = new CommandResult(lastPage, true);
            } catch (ServiceException e) {
                throw new CommandException("BasketAddCommand: error while adding song to basket", e);
            }
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
