package by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.AdminCommand;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;
import java.util.Set;

public class AdminCompilationPageCommand extends AdminCommand implements Command {
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
        Set<Song> songs = user.getBasket().getSongs();
        if (songs.size() > 0) {
            List<String> compilationTypes;
            try {
                compilationTypes = AudioContentService.INSTANCE.findContentDescription(ContentType.COMPILATION);
            } catch (ServiceException e) {
                throw new CommandException("AdminCompilationPageCommand: error while searching content", e);
            }
            content.addRequestAttribute(AttributeName.COMPILATION_CONTENT, user.getBasket().getSongs());
            content.addRequestAttribute(AttributeName.COMPILATION_TYPES_LIST, compilationTypes);
            content.addRequestAttribute(AttributeName.SHOW_COMPILATION_CONTROLS, true);
            return new CommandResult(PagePath.FORWARD_ADMIN_COMPILATION_CREATION, false);
        } else {
            content.addRequestAttribute(AttributeName.SHOW_COMPILATION_CONTROLS, false);
            return new CommandResult(PagePath.FORWARD_ADMIN_COMPILATION_CREATION, false);
        }
    }
}
