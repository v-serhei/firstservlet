package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;
import by.verbitsky.servletdemo.util.FieldDataValidator;

import java.util.Optional;

public class LoginCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        CommandResult result;
        boolean loginFail = true;
        String userName = content.getRequestParameter(ParameterName.USER_NAME);
        String password = content.getRequestParameter(ParameterName.USER_PASSWORD_FIRST);
        //check user data input
        if (!FieldDataValidator.validateUserName(userName) && !FieldDataValidator.validateUserPassword(password)) {
            content.addRequestAttribute(AttributeName.LOGIN_FAILED, loginFail);
            result = new CommandResult(PagePath.LOGIN_PAGE, false);
            return result;
        }
        //searching user in db and return auth result
        Optional<User> user;
        Optional<String> passwordFromDb;
        try {
            user = UserServiceImpl.INSTANCE.findUserByName(userName);
            passwordFromDb = UserServiceImpl.INSTANCE.findUserPassword(userName);
            if (user.isPresent() && passwordFromDb.isPresent()) {
                if (passwordFromDb.get().equals(UserServiceImpl.INSTANCE.getHashedPassword(password))) {
                    user.get().setLoginStatus(true);
                    content.addSessionAttribute(AttributeName.SESSION_USER, user.get());
                    result = new CommandResult(PagePath.MAIN_PAGE, true);
                    loginFail = false;
                } else {
                    result = new CommandResult(PagePath.LOGIN_PAGE, false);
                }
            } else {
                result = new CommandResult(PagePath.LOGIN_PAGE, false);
            }
        } catch (ServiceException e) {
            throw new CommandException("LoginCommand: execution error", e);
        }
        content.addRequestAttribute(AttributeName.LOGIN_FAILED, loginFail);
        return result;
    }
}