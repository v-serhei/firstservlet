package by.verbitsky.servletdemo.util;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Class FieldDataValidator is utility class for user inputs validation
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public class FieldDataValidator {
    private static final String REGEX_USERNAME = "[a-zA-Z\\d]{1,40}";
    private static final String REGEX_PARAMETER = "[а-яА-Яa-zA-Z\\d_/’@'!.,+()\\-\\s]{0,80}";
    private static final String REGEX_TEXTAREA = "[а-яА-Яa-zA-Z\\d_/@’'!.,+()\\-\\s]{0,200}";
    private static final String REGEX_PASSWORD = "[a-zA-Z\\d_`/,#%:;=’@&$|+*.?\\-\\s]{6,40}";

    private FieldDataValidator() {
    }

    public static boolean isValidUserName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches(REGEX_USERNAME);
    }

    public static boolean isValidUserEmail(String email) {
        if (email == null) {
            return false;
        }
        return validateEmailRegex(email);
    }

    public static boolean isValidUserPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.matches(REGEX_PASSWORD);
    }

    public static boolean validateRequestParameter(String parameter) {
        if (parameter == null) {
            return false;
        }
        return parameter.matches(REGEX_PARAMETER);
    }

    public static boolean validateRequestTextAreaParameter(String textArea) {
        if (textArea == null) {
            return false;
        }
        return textArea.matches(REGEX_TEXTAREA);
    }

    private static boolean validateEmailRegex(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}