package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.AdminCommand;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;

public class AlbumManagementPageCommand extends AdminCommand implements Command {
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
            List<AudioContent> albumList = AudioContentService.INSTANCE.findAllContent(ContentType.ALBUM);
            List<AudioContent> singerList = AudioContentService.INSTANCE.findAllContent(ContentType.SINGER);
            content.addRequestAttribute(AttributeName.ALBUM_LIST, albumList);
            content.addRequestAttribute(AttributeName.SINGER_LIST, singerList);
        } catch (ServiceException e) {
            throw new CommandException("AlbumManagementPageCommand: error while searching content", e);
        }
        return new CommandResult(PagePath.FORWARD_ADMIN_ALBUM_MANAGEMENT, false);
    }
}