package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.UserBuilder;
import by.verbitsky.servletdemo.entity.impl.UserBuilderImpl;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.FieldDataValidator;

public class RegisterCommand implements Command {
    private static final String REG_ERROR_MESSAGE_DIFFERENT_PASSWORDS = "reg.error.message.different.passwords";
    private static final String REG_ERROR_MESSAGE_EMAIL_EXIST = "reg.error.message.exist.email";
    private static final String REG_ERROR_MESSAGE_EMAIL_NOT_MATCHES_REGEX = "reg.error.message.email.regex";
    private static final String REG_ERROR_MESSAGE_USER_EXIST = "reg.error.message.exist.user";
    private static final String REG_ERROR_MESSAGE_WRONG_EMAIL = "reg.error.message.wrong.email";
    private static final String REG_ERROR_MESSAGE_WRONG_PASSWORD = "reg.error.message.wrong.password";
    private static final String REG_ERROR_MESSAGE_WRONG_USER_NAME = "reg.error.message.wrong.name";
    //default User field values
    private static final int DEFAULT_BLOCKED_STATUS = 0; //non-blocked status as default user status
    private static final int DEFAULT_DISCOUNT_VALUE = 0;
    private static final int DEFAULT_USER_ROLE_ID = 2; //registeredUser as default user role

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        if (content == null) {
            throw new CommandExecutionException("RegisterCommand: received null content");
        }
        CommandResult result;
        String userName = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_NAME);
        String fPassword = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);
        String sPassword = content.getRequestParameter(ParameterNames.REGISTRATION_USER_PASSWORD_SECOND);
        String userMail = content.getRequestParameter(ParameterNames.REGISTRATION_USER_EMAIL);

        if (userName == null || fPassword == null || sPassword == null || userMail == null) {
            throw new CommandExecutionException("RegisterCommand: received one or more null parameter in request");
        }
        //check user name field
        if (!FieldDataValidator.validateUserName(userName)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_WRONG_USER_NAME);
            result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
            return result;
        }
        try {
            User fUser = UserService.INSTANCE.findUserByName(userName);
            if (fUser != null) {
                content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
                content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_USER_EXIST);
                result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
                return result;
            }
        } catch (PoolException | DaoException e) {
            throw new CommandExecutionException("RegisterCommand: unable to get user from db");
        }
        //check password field
        if (!FieldDataValidator.validateUserPasswords(fPassword, sPassword)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_WRONG_PASSWORD);
            result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
            return result;
        }
        if (!fPassword.equals(sPassword)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_DIFFERENT_PASSWORDS);
            result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
            return result;
        }

        //check user email field
        if (!FieldDataValidator.validateEmailRegex(userMail)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_EMAIL_NOT_MATCHES_REGEX);
            result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
            return result;
        }
        if (!FieldDataValidator.validateUserEmail(userMail)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_WRONG_EMAIL);
            result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
            return result;
        }
        try {
            User user = UserService.INSTANCE.findUserByEmail(userMail);
            if (user != null) {
                content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_FAIL, true);
                content.addRequestAttribute(AttributesNames.REQUEST_ATTR_REGISTRATION_ERROR_MSG, REG_ERROR_MESSAGE_EMAIL_EXIST);
                result = new CommandResult(ProjectPages.FORWARD_REGISTRATION_PAGE, false);
                return result;
            }
        } catch (PoolException | DaoException e) {
            throw new CommandExecutionException("RegisterCommand: unable to get user from db");
        }

        User sessionUser = (User) content.getSessionAttribute(AttributesNames.SESSION_ATTR_USER);
        if (sessionUser == null) {
            throw new CommandExecutionException("RegisterCommand: unable to get user from session");
        }
        if (sessionUser.getLoginStatus()) {
            result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
            return result;
        }

        UserBuilder builder = new UserBuilderImpl();
        builder.setUserName(userName);
        builder.setUserPassword(UserService.INSTANCE.getHashedPassword(fPassword));
        builder.setEmail(userMail);
        builder.setBlockedStatus(DEFAULT_BLOCKED_STATUS);
        builder.setDiscount(DEFAULT_DISCOUNT_VALUE);
        builder.setRoleId(DEFAULT_USER_ROLE_ID);
        User registeredUser = builder.buildUser();
        try {
            boolean insert = UserService.INSTANCE.addRegisteredUser(registeredUser);
            if (insert) {
                result = new CommandResult(ProjectPages.REDIRECT_LOGIN_PAGE, true);
            } else {
                //todo дописать причину редиректа
                result = new CommandResult(ProjectPages.REDIRECT_ERROR_PAGE, true);
            }
        } catch (PoolException | DaoException e) {
            throw new CommandExecutionException("RegisterCommand: error while adding user to data base");
        }
        return result;
    }
}
