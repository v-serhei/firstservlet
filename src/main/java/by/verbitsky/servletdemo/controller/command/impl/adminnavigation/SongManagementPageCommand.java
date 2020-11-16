package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.AdminCommand;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;

public class SongManagementPageCommand extends AdminCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
       if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
       if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        clearOperationMessageAttributes(content);
        try {
            List<AudioContent> songList = AudioContentService.INSTANCE.findAllContent(ContentType.SONG);
            List<AudioContent> singerList = AudioContentService.INSTANCE.findAllContent(ContentType.SINGER);
            List<AudioContent> albumList = AudioContentService.INSTANCE.findAllContent(ContentType.ALBUM);
            List<AudioContent> genreList = AudioContentService.INSTANCE.findAllContent(ContentType.GENRE);
            content.addRequestAttribute(AttributeName.ALBUM_LIST, albumList);
            content.addRequestAttribute(AttributeName.GENRE_LIST, genreList);
            content.addRequestAttribute(AttributeName.SINGER_LIST, singerList);
            content.addRequestAttribute(AttributeName.SONG_LIST, songList);
        } catch (ServiceException e) {
            throw new CommandException("SongManagementPageCommand: error while searching content", e);
        }
        return new CommandResult(PagePath.FORWARD_ADMIN_SONG_MANAGEMENT, false);
    }
}