package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;

import java.util.Locale;

public class SwitchLanguageCommand implements Command {

    private static final String EN_LANGUAGE = "en";
    private static final String RU_COUNTRY = "RU";
    private static final String RU_LANGUAGE = "ru";

    @Override
    public CommandResult execute(SessionRequestContent content) {
        CommandResult result;
        String langParameter = content.getRequestParameter(ParameterNames.ACTION);
        Locale locale = defineLocale(langParameter);
        content.addSessionAttribute(AttributeNames.SESSION_ATTR_LOCALE, locale);
        String lastPage = (String) content.getSessionAttribute(AttributeNames.SESSION_ATTR_LAST_URI);
        if (lastPage == null || lastPage.isEmpty()) {
            result = new CommandResult(PagePaths.MAIN_PAGE, true);
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