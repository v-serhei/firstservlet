package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

public class RegisterCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        CommandResult result;
        boolean isUserInputsIncorrect;
        String userName = content.getRequestParameter(ParameterName.USER_NAME);
        String firstPassword = content.getRequestParameter(ParameterName.USER_PASSWORD_FIRST);
        String secondPassword = content.getRequestParameter(ParameterName.USER_PASSWORD_SECOND);
        String userEmail = content.getRequestParameter(ParameterName.USER_EMAIL);
        try {
            isUserInputsIncorrect = UserServiceImpl.INSTANCE.validateUserInputs(userName, firstPassword, secondPassword, userEmail, content);
            if (isUserInputsIncorrect) {
                result = new CommandResult(PagePath.FORWARD_REGISTRATION_PAGE, false);
            } else {
                User registeredUser = new User();
                registeredUser.setUserName(userName);
                registeredUser.setEmail(userEmail);
                try {
                    boolean addResult = UserServiceImpl.INSTANCE.addRegisteredUser(registeredUser, firstPassword);
                    if (addResult) {
                        result = new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
                    } else {
                        content.addSessionAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeValue.DEFAULT_COMMAND_ERROR_MESSAGE);
                        content.addSessionAttribute(AttributeName.REQUESTED_URL, content.getRequest().getRequestURI());
                        throw new CommandException("RegisterCommand: error while adding user to data base");
                    }
                } catch (ServiceException e) {
                    content.addSessionAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeValue.DEFAULT_COMMAND_ERROR_MESSAGE);
                    content.addSessionAttribute(AttributeName.REQUESTED_URL, content.getRequest().getRequestURI());
                    throw new CommandException("RegisterCommand: error while adding user to data base", e);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException("RegisterCommand: Error while validating user inputs", e);
        }
        return result;
    }
}