package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.UserService;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

public class LoginCommand implements Command {
    private UserService userService = UserServiceImpl.INSTANCE;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User sessionUser = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (sessionUser.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
        }
        CommandResult result;
        boolean loginFail = true;
        String userName = content.getRequestParameter(ParameterName.USER_NAME);
        String password = content.getRequestParameter(ParameterName.USER_PASSWORD_FIRST);
        //check user data input
        if (!userService.validateLoginInputs(userName, password, content)) {
            content.addRequestAttribute(AttributeName.LOGIN_FAILED, loginFail);
            result = new CommandResult(PagePath.FORWARD_LOGIN_PAGE, false);
            return result;
        }
        try {
            if (userService.checkBlockedStatus (userName)) {
                content.addSessionAttribute(AttributeName.OPERATION_TYPE, AttributeValue.LOGIN);
                content.addSessionAttribute(AttributeName.OPERATION_RESULT, AttributeValue.LOGIN_FAIL);
                content.addSessionAttribute(AttributeName.OPERATION_MESSAGE, AttributeValue.LOGIN_BLOCKED);
                content.addSessionAttribute(AttributeName.OPERATION_BUTTON_CAPTION, AttributeValue.BUTTON_CAPTION_BACK);
                content.addSessionAttribute(AttributeName.OPERATION_BUTTON_LINK, PagePath.REDIRECT_MAIN_PAGE);
                return new CommandResult(PagePath.FORWARD_RESULT_PAGE, false);
            }
            if (userService.checkUserLogin (userName, password, content)) {
                loginFail = false;
                result = new CommandResult(PagePath.REDIRECT_MAIN_PAGE, true);
            }else {
                result = new CommandResult(PagePath.FORWARD_LOGIN_PAGE, false);
            }
        } catch (ServiceException e) {
            throw new CommandException("LoginCommand: execution error", e);
        }
        content.addRequestAttribute(AttributeName.LOGIN_FAILED, loginFail);
        return result;
    }
}