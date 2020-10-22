package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.UserBuilder;
import by.verbitsky.servletdemo.entity.impl.UserBuilderImpl;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;
import by.verbitsky.servletdemo.util.FieldDataValidator;

import java.util.Optional;

public class RegisterCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        User sessionUser = (User) content.getSessionAttribute(AttributeNames.SESSION_ATTR_USER);
        if (sessionUser == null) {
            content.addSessionAttribute(AttributeNames.REQUEST_ATTR_COMMAND_ERROR_MESSAGE,
                    AttributeValues.DEFAULT_COMMAND_EXECUTE_ERROR_MESSAGE);
            content.addSessionAttribute(AttributeNames.REQUEST_ATTR_REQUESTED_URL,
                    content.getRequest().getRequestURI());
            throw new CommandExecutionException("RegisterCommand: unable to get user from session");
        }
        CommandResult result;
        if (sessionUser.getLoginStatus()) {
            result = new CommandResult(PagePaths.MAIN_PAGE, true);
            return result;
        }
        boolean isUserInputsIncorrect = false;
        String userName = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_NAME);
        String firstPassword = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);
        String secondPassword = content.getRequestParameter(ParameterNames.REGISTRATION_USER_PASSWORD_SECOND);
        String userEmail = content.getRequestParameter(ParameterNames.REGISTRATION_USER_EMAIL);

        //checking user data inputs
        if (checkUserName(userName, content)) {
            isUserInputsIncorrect = true;
        }
        if (checkUserPasswords(firstPassword, secondPassword, content)) {
            isUserInputsIncorrect = true;
        }
        if (checkUserEmail (userEmail, content)) {
            isUserInputsIncorrect = true;
        }
        if (isUserInputsIncorrect) {
            result = new CommandResult(PagePaths.REGISTRATION_PAGE, false);
        } else {
            result = addUserToDataBase (userName, firstPassword, userEmail, content);
        }
        return result;
    }

    private CommandResult addUserToDataBase(String userName, String firstPassword, String userEmail,
                                            SessionRequestContent content) throws CommandExecutionException {
        CommandResult result;
        UserBuilder builder = new UserBuilderImpl();
        builder.setUserName(userName);
        builder.setEmail(userEmail);
        User registeredUser = builder.buildUser();
        try {
            UserServiceImpl.INSTANCE.addRegisteredUser(registeredUser, UserServiceImpl.INSTANCE.getHashedPassword(firstPassword));
            result = new CommandResult(PagePaths.LOGIN_PAGE, true);
        } catch (PoolException | ServiceException e) {
            content.addSessionAttribute(AttributeNames.REQUEST_ATTR_COMMAND_ERROR_MESSAGE,
                    AttributeValues.DEFAULT_COMMAND_EXECUTE_ERROR_MESSAGE);
            content.addSessionAttribute(AttributeNames.REQUEST_ATTR_REQUESTED_URL,
                    content.getRequest().getRequestURI());
            throw new CommandExecutionException("RegisterCommand: error while adding user to data base");
        }
        return result;
    }

    private boolean checkUserName(String userName, SessionRequestContent content) throws CommandExecutionException {
        boolean result = false;
        if (!FieldDataValidator.validateUserName(userName)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_NAME, true);
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_NAME_ERROR_MSG,
                    AttributeValues.REG_ERROR_MESSAGE_WRONG_USER_NAME);
            result = true;
        }
        try {
            Optional<User> fUser = UserServiceImpl.INSTANCE.findUserByName(userName);
            if (fUser.isPresent()) {
                content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_NAME, true);
                content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_NAME_ERROR_MSG,
                        AttributeValues.REG_ERROR_MESSAGE_USER_EXIST);
                result = true;
            }
        } catch (PoolException | ServiceException e) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_COMMAND_ERROR_MESSAGE,
                    AttributeValues.DEFAULT_COMMAND_EXECUTE_ERROR_MESSAGE);
            throw new CommandExecutionException("RegisterCommand: unable to get user from db");
        }
        return result;
    }

    private boolean checkUserPasswords(String firstPassword, String secondPassword, SessionRequestContent content) {
        boolean result = false;
        if (!FieldDataValidator.validateUserPasswords(firstPassword, secondPassword)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_PASSWORD, true);
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_PASSWORD_ERROR_MSG,
                    AttributeValues.REG_ERROR_MESSAGE_WRONG_PASSWORD);
            result = true;
        }
        if (!firstPassword.equals(secondPassword)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_PASSWORD, true);
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_PASSWORD_ERROR_MSG,
                    AttributeValues.REG_ERROR_MESSAGE_DIFFERENT_PASSWORDS);
            result = true;
        }
        return result;
    }

    private boolean checkUserEmail(String userEmail, SessionRequestContent content) throws CommandExecutionException {
        boolean result = false;
        if (!FieldDataValidator.validateEmailRegex(userEmail)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_EMAIL, true);
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_EMAIL_ERROR_MSG,
                    AttributeValues.REG_ERROR_MESSAGE_EMAIL_NOT_MATCHES_REGEX);
            result = true;
        }
        if (!FieldDataValidator.validateUserEmail(userEmail)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_EMAIL, true);
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_EMAIL_ERROR_MSG,
                    AttributeValues.REG_ERROR_MESSAGE_WRONG_EMAIL);
            result = true;
        }
        try {
            Optional<User> user = UserServiceImpl.INSTANCE.findUserByEmail(userEmail);
            if (user.isPresent()) {
                content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_FAIL_WRONG_EMAIL, true);
                content.addRequestAttribute(AttributeNames.REQUEST_ATTR_REGISTRATION_EMAIL_ERROR_MSG,
                        AttributeValues.REG_ERROR_MESSAGE_EMAIL_EXIST);
                result = true;
            }
        } catch (PoolException | ServiceException e) {
            throw new CommandExecutionException("RegisterCommand: unable to get user from db");
        }
        return result;
    }
}