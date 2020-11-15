package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.math.BigDecimal;

public class AdminUpdateSongCommand implements Command {
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
            String sSongId = content.getRequestParameter(ParameterName.SONG_ID);
            String updatedSongTitle = content.getRequestParameter(ParameterName.UPDATE_SONG);
            String sSingerId = content.getRequestParameter(ParameterName.SINGER_ID);
            String sAlbumId = content.getRequestParameter(ParameterName.ALBUM_ID);
            String sGenreId = content.getRequestParameter(ParameterName.GENRE_ID);
            String sSongPrice = content.getRequestParameter(ParameterName.SONG_PRICE);
            if (sSongId != null && updatedSongTitle != null &&
                    sSingerId != null && sAlbumId != null &&
                    sGenreId != null && sSongPrice != null) {

                Song song = new Song();
                song.setId(Long.parseLong(sSongId));
                song.setSongTitle(updatedSongTitle);
                song.setSingerId(Long.parseLong(sSingerId));
                song.setAlbumId(Long.parseLong(sAlbumId));
                song.setGenreId(Long.parseLong(sGenreId));
                song.setPrice(new BigDecimal(sSongPrice));
                boolean updateResult = AudioContentService.INSTANCE.updateSong(song);
                if (updateResult) {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_UPDATE_SUCCESSFUL);
                } else {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                            AttributeValue.ADMIN_CONTENT_UPDATE_SQL_ERROR);
                }

            } else {
                content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                        AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
            }
        } catch (
                ServiceException e) {
            throw new CommandException("UpdateSongCommand: error while searching content", e);
        } catch (
                NumberFormatException ex) {
            content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                    AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_SONG_MANAGEMENT_PAGE, true);
    }
}