package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class RegisterCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        //UserService.INSTANCE.processRegistration(content);
        CommandResult result;

        User registeredUser = (User) content.getSessionAttribute(AttributesNames.SESSION_ATTR_USER);
        if (registeredUser != null && !registeredUser.getLoginStatus()) {

            result = new CommandResult(ProjectPages.REDIRECT_LOGIN_PAGE, true);
 /*
            String userName = content.getRequestParameter(PageParameterNames.LOGIN_REGISTRATION_USER_NAME);

            boolean regErrorResult = true;

        if (userName == null || userName.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_USER_NAME);
            return;
        }
            try {
                User user = findUserByName(userName);
                if (user != null) {
                    setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_USER, regErrorResult);
                    return;
                }
            } catch (PoolException | DaoException e) {
                //todo generate error page
            }
            registeredUser.setUserName(userName);
            String firstPassword = content.getRequestParameter(PageParameterNames.LOGIN_REGISTRATION_USER_PASSWORD_FIRST);
            String secondPassword = content.getRequestParameter(PageParameterNames.REGISTRATION_USER_PASSWORD_SECOND);

        if (firstPassword == null || firstPassword.isEmpty() || secondPassword == null || secondPassword.isEmpty()) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EMPTY_PASSWORD);
            return;
        }
            if (!firstPassword.equals(secondPassword)) {
                setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_DIFFERENT_PASSWORDS, regErrorResult);
                return;
            }
            registeredUser.setUserPassword(getHashedPassword(firstPassword));
            String email = content.getRequestParameter(PageParameterNames.REGISTRATION_USER_EMAIL);

        if (!validateUserEmail(email)) {
            setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_WRONG_EMAIL);
            return;
        }
            try {
                User user = findUserByEmail(email);
                if (user != null) {
                    setWrongRegistrationResult(content, REGISTER_ERROR_MESSAGE_EXIST_EMAIL, regErrorResult);
                    return;
                }
            } catch (PoolException | DaoException e) {
                //todo generate error page
            }
            registeredUser.setEmail(email);
            registeredUser.setBlockedStatus(DEFAULT_BLOCKED_STATUS);
            registeredUser.setDiscount(DEFAULT_USER_DISCOUNT);
            registeredUser.setRoleId(DEFAULT_USER_ROLE_ID);
            //String resultPage = resourcesManager.getProperty(LOGIN_PAGE);
            try {
                addRegisteredUser(registeredUser);
            } catch (PoolException | DaoException e) {
                // todo generate error page
            }
            regErrorResult = false;
            // content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_RESULT), regErrorResult);
            //content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPage);
            content.pushAttributesToRequest(content.getRequest());
            content.pushAttributesToSession(content.getRequest());


            result = new CommandResult(ProjectPages.REDIRECT_LOGIN_PAGE, true);
            */
        } else {
            //todo возможно послать на страницу ошибки с месагой или взять из истории страницу и кинуть туда
            result = new CommandResult(ProjectPages.REDIRECT_ERROR_PAGE, true);
        }


        return result;
    }


    private void setWrongRegistrationResult(SessionRequestContent content, String message, boolean regErrorResult) {
       /* String resultPageUrl = resourcesManager.getProperty(REGISTER_PAGE);
        content.addRequestAttribute(resourcesManager.getProperty(RESULT_PAGE), resultPageUrl);
        content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_ERROR_MESSAGE), message);
        content.addRequestAttribute(resourcesManager.getProperty(ATTR_REQUEST_REG_RESULT), regErrorResult);

        */
    }


}
