package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;
import by.verbitsky.servletdemo.service.impl.UserService;
import by.verbitsky.servletdemo.util.FieldDataValidator;

public class LoginCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        if (content == null) {
            throw new CommandExecutionException("LoginCommand: received null content");
        }
        CommandResult result;
        boolean loginFail = true;
        String userName = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_NAME);
        String password = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);
        //check user data input
        if (!FieldDataValidator.validateUserName(userName) && !FieldDataValidator.validateUserPassword(password)) {
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
            result = new CommandResult(ProjectPages.FORWARD_LOGIN_PAGE, false);
            return result;
        }
        //check login status
        User sessionUser = (User) content.getSession().getAttribute(AttributesNames.SESSION_ATTR_USER);
        if (sessionUser == null) {
            throw new CommandExecutionException("LoginCommand: session attr \"User\" doesn't exist");
        }
        //if already logged in, then redirect to home page
        if (sessionUser.getLoginStatus()) {
            loginFail = false;
            content.addRequestAttribute(AttributesNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
            result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
            return result;
        }
        //searching user in db and return auth result
        try {
            User user = UserService.INSTANCE.findUserByName(userName);
            if (user != null) {
                //if passwords are equals - set logged in status true
                if (user.getUserPassword().equals(UserService.INSTANCE.getHashedPassword(password))) {
                    user.setLoginStatus(true);
                    //add user to session attr
                    content.addSessionAttribute(AttributesNames.SESSION_ATTR_USER, user);
                    result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
                    loginFail = false;
                } else {
                    result = new CommandResult(ProjectPages.FORWARD_LOGIN_PAGE, false);
                }
            } else {
                result = new CommandResult(ProjectPages.FORWARD_LOGIN_PAGE, false);
            }
        } catch (PoolException | DaoException e) {
            throw new CommandExecutionException("LoginCommand: execution error", e);
        }
        content.addRequestAttribute(AttributesNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
        return result;
    }
}