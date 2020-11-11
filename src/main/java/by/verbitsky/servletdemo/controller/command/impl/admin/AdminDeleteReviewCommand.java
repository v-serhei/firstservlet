package by.verbitsky.servletdemo.controller.command.impl.admin;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.Optional;

public class AdminDeleteReviewCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        if (user != null && user.getLoginStatus()) {
            try {
                long deletedId = Long.parseLong(content.getRequestParameter(ParameterName.REVIEW_ID));
                Optional<AudioContent> deletedReview = AudioContentService.INSTANCE.findContentById(ContentType.REVIEW, deletedId);
                if (deletedReview.isPresent()) {
                    boolean deleteResult = AudioContentService.INSTANCE.deleteContentById(ContentType.REVIEW, deletedId);
                    if (deleteResult) {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_REVIEW_DELETE_SUCCESSFUL);
                    }else {
                        content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_REVIEW_DELETE_SQL_ERROR);
                    }
                }else {
                    content.addSessionAttribute(AttributeName.ADMIN_OPERATION_RESULT_MSG, AttributeValue.ADMIN_REVIEW_DELETE_NOT_FOUND);
                }
            } catch (ServiceException e) {
                throw new CommandException("UserReviewsPageCommand: error while searching user reviews", e);
            } catch (NumberFormatException ex) {
                throw new CommandException("UserReviewsPageCommand: wrong parameter reviewId", ex);
            }
            content.addSessionAttribute(AttributeName.ADMIN_OPERATION_MESSAGE_FLAG, true);
            return new CommandResult(PagePath.REDIRECT_ADMIN_REVIEW_MANAGEMENT_PAGE, true);
        } else {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
    }
}
