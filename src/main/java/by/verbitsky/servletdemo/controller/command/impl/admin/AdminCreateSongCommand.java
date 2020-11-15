package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Song;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AdminCreateSongCommand implements Command {
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
            String songTitle = content.getRequestParameter(ParameterName.SONG);
            String sSingerId = content.getRequestParameter(ParameterName.SINGER_ID);
            String sAlbumId = content.getRequestParameter(ParameterName.ALBUM_ID);
            String sGenreId = content.getRequestParameter(ParameterName.GENRE_ID);
            String sSongPrice = content.getRequestParameter(ParameterName.SONG_PRICE);
            String filePath = (String) content.getRequestAttribute(AttributeName.UPLOAD_FILE_PATH);
            if (filePath !=null && !filePath.isEmpty() && songTitle != null &&
                    sSingerId != null && sAlbumId != null &&
                    sGenreId != null && sSongPrice != null) {

                Song song = new Song();
                song.setSongTitle(songTitle);
                song.setSingerId(Long.parseLong(sSingerId));
                song.setAlbumId(Long.parseLong(sAlbumId));
                song.setGenreId(Long.parseLong(sGenreId));
                song.setPrice(new BigDecimal(sSongPrice));
                song.setFilePath(filePath);
                song.setUploadDate(LocalDate.now());
                boolean createResult = AudioContentService.INSTANCE.createSong(song);
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
        } catch (
                ServiceException e) {
            throw new CommandException("CreateSongCommand: error while searching content", e);
        } catch (
                NumberFormatException ex) {
            content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG,
                    AttributeValue.ADMIN_CONTENT_CREATE_WRONG_PARAMETERS);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_SONG_MANAGEMENT_PAGE, true);
    }
}