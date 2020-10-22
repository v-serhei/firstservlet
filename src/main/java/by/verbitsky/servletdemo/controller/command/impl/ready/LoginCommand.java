package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;
import by.verbitsky.servletdemo.util.FieldDataValidator;

import java.util.Optional;

public class LoginCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        CommandResult result;
        boolean loginFail = true;
        String userName = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_NAME);
        String password = content.getRequestParameter(ParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);
        //check user data input
        if (!FieldDataValidator.validateUserName(userName) && !FieldDataValidator.validateUserPassword(password)) {
            content.addRequestAttribute(AttributeNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
            result = new CommandResult(PagePaths.LOGIN_PAGE, false);
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
                    content.addSessionAttribute(AttributeNames.SESSION_ATTR_USER, user.get());
                    result = new CommandResult(PagePaths.MAIN_PAGE, true);
                    loginFail = false;
                } else {
                    result = new CommandResult(PagePaths.LOGIN_PAGE, false);
                }
                System.out.println();
            } else {
                result = new CommandResult(PagePaths.LOGIN_PAGE, false);
            }
        } catch (ServiceException | PoolException e) {
            throw new CommandExecutionException("LoginCommand: execution error", e);
        }
        content.addRequestAttribute(AttributeNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
        return result;
    }
}