package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;

import java.util.Locale;

public class LanguageCommand implements Command {

    private static final String EN_LANGUAGE = "en";
    private static final String RU_COUNTRY = "RU";
    private static final String RU_LANGUAGE = "ru";

    @Override
    public CommandResult execute(SessionRequestContent content) {
        CommandResult result;
        String langParameter = content.getRequestParameter(ParameterName.ACTION);
        Locale locale = defineLocale(langParameter);
        content.addSessionAttribute(AttributeName.SESSION_LOCALE, locale);
        String lastPage = (String) content.getSessionAttribute(AttributeName.SESSION_LAST_URI);
        if (lastPage == null || lastPage.isEmpty()) {
            result = new CommandResult(PagePath.MAIN_PAGE, true);
        } else {
            result = new CommandResult(lastPage, false);
        }
        return result;
    }

    private Locale defineLocale(String lang) {
        Locale locale;
        if (lang != null || !lang.isEmpty()) {
            switch (lang.toLowerCase()) {
                case EN_LANGUAGE: {
                    locale = Locale.ENGLISH;
                    break;
                }
                case RU_LANGUAGE: {
                    locale = new Locale(RU_LANGUAGE, RU_COUNTRY);
                    break;
                }
                default: locale = Locale.getDefault();
            }
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}