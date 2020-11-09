package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.util.Optional;

public class DeleteReviewCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (user != null && user.getLoginStatus()) {
            try {
                long deletedId = Long.parseLong(content.getRequestParameter(ParameterName.REVIEW_ID));
                Optional<AudioContent> deletedReview = AudioContentService.INSTANCE.findContentById(ContentType.REVIEW, deletedId);
                if (deletedReview.isPresent()) {
                    if (((Review) deletedReview.get()).getUserId() == user.getUserId() |
                            user.getRoleId() == UserServiceImpl.INSTANCE.getAdminRoleId()) {
                        boolean deleteResult = AudioContentService.INSTANCE.deleteContentById(ContentType.REVIEW, deletedId);
                        if (deleteResult) {
                            content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_COMPLETED);
                            content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.REVIEW_DELETE_SUCCESSFUL);
                        } else {
                            content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                            content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.REVIEW_DELETE_FAIL);
                        }
                        content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.REVIEW_DELETE);
                        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_BACK);
                        content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_REVIEWS_PAGE);
                        return new CommandResult(PagePath.REDIRECT_ORDER_REMOVE_RESULT_PAGE, true);
                    } else {
                        return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
                    }
                }else {
                    content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.REVIEW_DELETE);
                    content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                    content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.REVIEW_NOT_EXIST);
                    content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_BACK);
                    content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_USER_REVIEWS_PAGE);
                    return new CommandResult(PagePath.REDIRECT_ORDER_REMOVE_RESULT_PAGE, true);
                }
            } catch (ServiceException e) {
                throw new CommandException("UserReviewsPageCommand: error while searching user reviews", e);
            } catch (NumberFormatException ex) {
                throw new CommandException("UserReviewsPageCommand: wrong parameter reviewId", ex);
            }
        } else {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
    }
}
