package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Album;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;
import by.verbitsky.servletdemo.util.ParameterParser;

import java.time.LocalDate;
import java.util.Optional;

public class AdminUpdateAlbumCommand implements Command {
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
            String selectedAlbumTitle = content.getRequestParameter(ParameterName.ALBUM);
            String updateAlbumTitle = content.getRequestParameter(ParameterName.UPDATED_ALBUM);
            String singerName = content.getRequestParameter(ParameterName.SINGER);
            String updateDate = content.getRequestParameter(ParameterName.UPDATED_ALBUM_DATE);
            if (selectedAlbumTitle != null || !selectedAlbumTitle.isEmpty() ||
                    updateAlbumTitle != null || !updateAlbumTitle.isEmpty() ||
                    singerName != null || !singerName.isEmpty() ||
                    updateDate != null || !updateDate.isEmpty()) {
                Optional<AudioContent> updatedAlbum = AudioContentService.INSTANCE.findContentByTitle(ContentType.ALBUM, selectedAlbumTitle);
                Optional<AudioContent> singer = AudioContentService.INSTANCE.findContentByTitle(ContentType.SINGER, singerName);
                if (updatedAlbum.isPresent() && singer.isPresent()) {
                    ((Album) updatedAlbum.get()).setAlbumTitle(updateAlbumTitle);
                    ((Album) updatedAlbum.get()).setSingerName(singerName);
                    Optional<LocalDate> albumDate = ParameterParser.parseDateFromParameter(updateDate);
                    albumDate.ifPresent(localDate -> ((Album) updatedAlbum.get()).setAlbumDate(localDate));
                    boolean updateResult = AudioContentService.INSTANCE.updateContent(ContentType.ALBUM, updatedAlbum.get());
                    if (updateResult) {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_UPDATE_SUCCESSFUL);
                    } else {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                                AttributeValue.ADMIN_CONTENT_UPDATE_SQL_ERROR);
                    }
                } else {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_UPDATE_NOT_FOUND);
                }
            } else {
                content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                        AttributeValue.ADMIN_CONTENT_UPDATE_WRONG_PARAMETERS);
            }
        } catch (ServiceException e) {
            throw new CommandException("UpdateAlbumCommand: error while searching content", e);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_ALBUM_MANAGEMENT_PAGE, true);
    }
}