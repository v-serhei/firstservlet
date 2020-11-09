package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;

public class UserReviewsPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        CommandResult result;
        if (user != null && user.getLoginStatus()) {
            try {
                List<AudioContent> userOrders = AudioContentService.INSTANCE.findUserReviews(user);
                content.addRequestAttribute(AttributeName.REVIEW_CONTENT, userOrders);
                content.addRequestAttribute(AttributeName.REVIEW_SEARCH_COUNT_RESULT, userOrders.size());
                result = new CommandResult(PagePath.FORWARD_USER_REVIEWS_PAGE, false);
            } catch (ServiceException e) {
                throw new CommandException("UserReviewsPageCommand: error while searching user reviews", e);
            }
        } else {
            result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        return result;
    }
}
