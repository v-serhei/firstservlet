package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.time.LocalDate;
import java.util.List;

public class AdminCreateCompilationCommand implements Command {
    private AudioContentService contentService = AudioContentService.INSTANCE;

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
            String compilationType = content.getRequestParameter(ParameterName.COMPILATION_TYPE_NAME);
            String compilationTitle = content.getRequestParameter(ParameterName.COMPILATION);
            LocalDate compilationDate = LocalDate.now();
            List<String> compilationTypes = contentService.findContentDescription(ContentType.COMPILATION);
            if (compilationType != null && !compilationType.isEmpty() &&
                    compilationTitle != null && !compilationTitle.isEmpty() &&
                    compilationTypes.contains(compilationType) && !user.getBasket().isEmpty()) {
                boolean createResult = contentService.createCompilation(compilationTitle, compilationType, compilationDate, user);
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
        } catch (ServiceException e) {
            throw new CommandException("CreateAlbumCommand: error while searching content", e);
        }
        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
        return new CommandResult(PagePath.REDIRECT_ADMIN_COMPILATION_PAGE, true);
    }
}