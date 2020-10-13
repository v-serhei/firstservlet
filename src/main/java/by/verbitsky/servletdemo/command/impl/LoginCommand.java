package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.PageParameterNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;
import by.verbitsky.servletdemo.service.impl.UserService;

public class LoginCommand implements Command {

    private static final String POST_METHOD = "post";
    private static final String RESPONSE_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String RESPONSE_CONTENT_TYPE_TEXT = "text/html";

    @Override
    public CommandResult execute(SessionRequestContent content) {
        CommandResult result;
        boolean loginFail = true;
        String method = content.getRequest().getMethod();

        //todo проверить валидатором
        //todo проверить не залогинен ли юзер
        String userName = content.getRequestParameter(PageParameterNames.LOGIN_REGISTRATION_USER_NAME);
        String password = content.getRequestParameter(PageParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);

        try {
            //looking for user in data base and compare user passwords
            User user = UserService.INSTANCE.findUserByName(userName);
            if (user != null) {
                //if passwords are equals - set logged in status true
                if (user.getUserPassword().equals(UserService.INSTANCE.getHashedPassword(password))) {
                    user.setLoginStatus(true);
                    user.setSession(content.getSession());
                    //add user to attr session
                    content.addSessionAttribute(AttributesNames.SESSION_ATTR_USER, user);
                    //set response header
                    result = new CommandResult(ProjectPages.REDIRECT_MAIN_PAGE, true);
                    loginFail = false;
                } else {
                    result = new CommandResult(ProjectPages.FORWARD_LOGIN_PAGE, false);
                }
            } else {
                result = new CommandResult(ProjectPages.FORWARD_LOGIN_PAGE, false);
            }
        } catch (PoolException | DaoException e) {
            result = new CommandResult(ProjectPages.REDIRECT_ERROR_PAGE, true);
        }
        content.addRequestAttribute(AttributesNames.REQUEST_ATTR_LOGIN_FAILED, loginFail);
        content.pushAttributesToRequest(content.getRequest());
        content.pushAttributesToSession(content.getRequest());
        return result;
    }
}