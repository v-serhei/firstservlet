package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

public class CreateReviewCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        String songTitle = content.getRequestParameter(ParameterName.SONG);
        String singerName = content.getRequestParameter(ParameterName.SINGER);
        String reviewText = content.getRequestParameter(ParameterName.REVIEW_TEXT);
        try {
            boolean result = AudioContentService.INSTANCE.createReview(user, songTitle, singerName, reviewText);
            if (result) {
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_COMPLETED);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.REVIEW_ADD_SUCCESSFUL);
            }else {
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.OPERATION_FAILED);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.REVIEW_ADD_FAIL);
            }
            content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.REVIEW_CREATION);
            content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_REVIEW);
            content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_REVIEW_PAGE);
            return new CommandResult(PagePath.REDIRECT_ORDER_REMOVE_RESULT_PAGE, true);
        } catch (ServiceException e) {
            throw new CommandException("CreateReviewCommand: error while creating review", e);
        }
    }
}
