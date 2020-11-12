package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Genre;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.Optional;

public class AdminUpdateGenreCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        try {
            String selectedGenreTitle = content.getRequestParameter(ParameterName.GENRE);
            String updateName = content.getRequestParameter(ParameterName.UPDATED_GENRE);
            if (updateName != null && !updateName.isEmpty()
                    && selectedGenreTitle != null && !selectedGenreTitle.isEmpty()) {
                Optional<AudioContent> updatedGenre = AudioContentService.INSTANCE.findContentByTitle(ContentType.GENRE, selectedGenreTitle);
                if (updatedGenre.isPresent()){
                    ((Genre)updatedGenre.get()).setGenreName(updateName);
                    boolean updateResult = AudioContentService.INSTANCE.updateContent(ContentType.GENRE, updatedGenre.get());
                    if (updateResult) {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_UPDATE_SUCCESSFUL);
                    }else {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_UPDATE_SQL_ERROR);
                    }
                }else {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_UPDATE_NOT_FOUND);
                }

            }else {
                content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                        AttributeValue.ADMIN_CONTENT_UPDATE_WRONG_PARAMETERS);
            }
        } catch (ServiceException e) {
            throw new CommandException("UpdateUserCommand: error while searching content", e);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_GENRE_MANAGEMENT_PAGE, true);
    }
}