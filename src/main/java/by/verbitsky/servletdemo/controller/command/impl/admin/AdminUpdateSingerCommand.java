package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Singer;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.Optional;

public class AdminUpdateSingerCommand implements Command {
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
            String selectedSingerName = content.getRequestParameter(ParameterName.SINGER);
            String updateName = content.getRequestParameter(ParameterName.UPDATED_SINGER);
            if (updateName != null && !updateName.isEmpty()
                    && selectedSingerName != null && !selectedSingerName.isEmpty()) {
                Optional<AudioContent> updatedSinger = AudioContentService.INSTANCE.findContentByTitle(ContentType.SINGER, selectedSingerName);
                if (updatedSinger.isPresent()){
                    ((Singer)updatedSinger.get()).setSingerName(updateName);
                    boolean updateResult = AudioContentService.INSTANCE.updateContent(ContentType.SINGER, updatedSinger.get());
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
        return new CommandResult(PagePath.REDIRECT_ADMIN_USER_MANAGEMENT_PAGE, true);
    }
}