package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.UserBuilder;
import by.verbitsky.servletdemo.entity.impl.UserBuilderImpl;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;
import by.verbitsky.servletdemo.util.FieldDataValidator;

import java.util.Optional;

public class RegisterCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        CommandResult result;
        boolean isUserInputsIncorrect = false;
        String userName = content.getRequestParameter(ParameterName.USER_NAME);
        String firstPassword = content.getRequestParameter(ParameterName.USER_PASSWORD_FIRST);
        String secondPassword = content.getRequestParameter(ParameterName.USER_PASSWORD_SECOND);
        String userEmail = content.getRequestParameter(ParameterName.USER_EMAIL);

        if (!FieldDataValidator.isValidRegistrationInputs(userName, firstPassword, secondPassword, userEmail, content)) {
            isUserInputsIncorrect = true;
        }
        if (isExistUserName(userName, content) | isExistEmail(userEmail, content)) {
            isUserInputsIncorrect = true;
        }
        if (isUserInputsIncorrect) {
            result = new CommandResult(PagePath.REGISTRATION_PAGE, false);
        } else {
            UserBuilder builder = new UserBuilderImpl();
            builder.setUserName(userName);
            builder.setEmail(userEmail);
            User registeredUser = builder.buildUser();
            try {
                UserServiceImpl.INSTANCE.addRegisteredUser(registeredUser, UserServiceImpl.INSTANCE.getHashedPassword(firstPassword));
                result = new CommandResult(PagePath.LOGIN_PAGE, true);
            } catch (ServiceException e) {
                content.addSessionAttribute(AttributeName.COMMAND_ERROR_MESSAGE, AttributeValue.DEFAULT_COMMAND_ERROR_MESSAGE);
                content.addSessionAttribute(AttributeName.REQUESTED_URL, content.getRequest().getRequestURI());
                throw new CommandException("RegisterCommand: error while adding user to data base");
            }
        }
        //todo сбрасывать фильтр
        return result;
    }

    private boolean isExistUserName(String userName, SessionRequestContent content) throws CommandException {
        Optional<User> result;
        try {
            result = UserServiceImpl.INSTANCE.findUserByName(userName);
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_NAME, result.isPresent());
        } catch (ServiceException e) {
            throw new CommandException("RegisterCommand: unable to check user data");
        }
        return result.isPresent();
    }

    private boolean isExistEmail(String userEmail, SessionRequestContent content) throws CommandException {
        boolean result;
        try {
            result = UserServiceImpl.INSTANCE.isExistEmail(userEmail);
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, result);
        } catch (ServiceException e) {
            throw new CommandException("RegisterCommand: unable to check user data");
        }
        return result;
    }
}