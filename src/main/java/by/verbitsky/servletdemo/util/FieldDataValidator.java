package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import org.apache.commons.validator.routines.EmailValidator;

public class FieldDataValidator {
    private static final String INPUT_REGEX = "";



    private FieldDataValidator() {
    }


    public static boolean isValidUserName(String name, SessionRequestContent content) {
        boolean result = true;
        if (!validateUserName(name)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_NAME, true);
            result = false;
        }
        return result;
    }

    public static boolean isValidUserPasswords(String firstPassword, String secondPassword, SessionRequestContent content) {
        boolean result = true;
        if (!validateUserPassword(firstPassword)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_PASSWORD, true);
            result = false;
        }
        if (!firstPassword.equals(secondPassword)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_DIFFERENT_PASSWORDS, true);
            result = false;
        }
        return result;
    }

    public static boolean isValidUserEmail(String email, SessionRequestContent content) {
        boolean result = true;
        if (!validateEmailRegex(email)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_EMAIL, true);
            result = false;
        }
        return result;
    }



    public static boolean validateUserName(String name) {
        //todo stub
        if (name == null || name.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateUserPassword(String password) {
        //todo stub
        if (password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateRequestParameter(String parameter) {
        if (parameter == null) {
            return false;
        }
        // TODO: 11.11.2020 дописать проверку входящих параметров
        return true;
    }

    private static boolean validateEmailRegex(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
