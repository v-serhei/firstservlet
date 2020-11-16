package by.verbitsky.servletdemo.controller.command.impl.adminnavigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.controller.command.impl.AdminCommand;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

public class ReviewManagementPageCommand extends AdminCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        if (!CommandPermissionValidator.isUserHasPermission(user, this)) {
            return new CommandResult(PagePath.FORWARD_ERROR_PAGE, false);
        }
        clearOperationMessageAttributes(content);
        String selectedUserName = content.getRequestParameter(ParameterName.SELECTED_USER);
        List<AudioContent> reviewList;
        if (selectedUserName != null && !selectedUserName.isEmpty()) {
            try {
                Optional<User> selectedUser = UserServiceImpl.INSTANCE.findUserByName(selectedUserName);
                if (selectedUser.isPresent()) {
                    reviewList = AudioContentService.INSTANCE.findUserReviews(selectedUser.get());
                    content.addRequestAttribute(AttributeName.ADMIN_SELECTED_USER, selectedUser.get());
                } else {
                    reviewList = AudioContentService.INSTANCE.findAllContent(ContentType.REVIEW);
                }
            } catch (ServiceException e) {
                throw new CommandException("ReviewManagementPageCommand: error while searching content", e);
            }
        } else {
            try {
                reviewList = AudioContentService.INSTANCE.findAllContent(ContentType.REVIEW);
            } catch (ServiceException e) {
                throw new CommandException("ReviewManagementPageCommand: error while searching content", e);
            }
        }
        try {
            List<User> userList = UserServiceImpl.INSTANCE.findAllUsers();
            content.addRequestAttribute(AttributeName.ADMIN_USER_LIST, userList);
        } catch (ServiceException e) {
            throw new CommandException("ReviewManagementPageCommand: error while searching all users", e);
        }
        content.addRequestAttribute(AttributeName.REVIEW_CONTENT, reviewList);
        content.addRequestAttribute(AttributeName.REVIEW_SEARCH_COUNT_RESULT, reviewList.size());
        return new CommandResult(PagePath.FORWARD_ADMIN_REVIEW_MANAGEMENT, false);
    }
}