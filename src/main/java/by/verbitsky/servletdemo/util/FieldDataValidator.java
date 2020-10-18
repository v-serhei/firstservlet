package by.verbitsky.servletdemo.util;

import org.apache.commons.validator.routines.EmailValidator;

public class FieldDataValidator {
    private FieldDataValidator() {
    }

    public static boolean validateUserName (String name) {
        //todo stub
        if (name==null || name.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateUserPassword (String password) {
        //todo stub
        if (password==null || password.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateUserPasswords (String fPassword, String sPassword) {
        //todo stub
        if (fPassword==null || sPassword == null || fPassword.isEmpty() || sPassword.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateUserEmail(String userMail) {
        if (userMail == null) {
            return false;
        }        //todo check other params
        return true;
    }


    public static boolean validateEmailRegex(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
