package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;
import by.verbitsky.servletdemo.util.ParameterParser;

import java.time.LocalDate;
import java.util.Optional;

public class AdminCreateAlbumCommand implements Command {
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
            String createAlbumTitle = content.getRequestParameter(ParameterName.CREATED_ALBUM);
            String singerName = content.getRequestParameter(ParameterName.SINGER);
            String createDate = content.getRequestParameter(ParameterName.CREATED_ALBUM_DATE);
            if (createAlbumTitle != null && !createAlbumTitle.isEmpty() &&
                    singerName != null && !singerName.isEmpty() &&
                    createDate != null && !createDate.isEmpty()) {
                Optional<LocalDate> albumDate = ParameterParser.parseDateFromParameter(createDate);
                if (!albumDate.isPresent()) {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
                    return new CommandResult(PagePath.REDIRECT_ADMIN_ALBUM_MANAGEMENT_PAGE, true);
                }
                Optional<AudioContent> singer = AudioContentService.INSTANCE.findContentByTitle(ContentType.SINGER, singerName);
                if (singer.isPresent()) {
                    boolean createResult = AudioContentService.INSTANCE.createAlbum(createAlbumTitle, singer.get().getId(), albumDate.get());
                    if (createResult) {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_CREATE_SUCCESSFUL);
                    } else {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_CREATE_SQL_ERROR);
                    }
                } else {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
                }
            } else {
                content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                        AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
            }

        } catch (ServiceException e) {
            throw new CommandException("CreateAlbumCommand: error while searching content", e);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_ALBUM_MANAGEMENT_PAGE, true);
    }
}