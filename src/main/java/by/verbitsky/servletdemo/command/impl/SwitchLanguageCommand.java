package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.exception.CommandExecutionException;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ParameterNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

import java.util.Locale;

public class SwitchLanguageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandExecutionException {
        if (content == null) {
            throw new CommandExecutionException("SwitchLanguageCommand: received null content");
        }
        CommandResult result;
        String lang = content.getRequestParameter(ParameterNames.ACTION);
        if (lang == null || lang.isEmpty()) {
            throw new CommandExecutionException("SwitchLanguageCommand: received null or empty parameter \"Lang\"");
        }
        Locale locale;
        switch (lang.toLowerCase()) {
            case "en": {
                locale = new Locale("en", "US");
                break;
            }
            case "ru": {
                locale = new Locale("ru", "RU");
                break;
            }
            default:
                locale = Locale.getDefault();
        }
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LOCALE, locale);
        String lastPage = (String) content.getSessionAttribute(AttributesNames.SESSION_ATTR_LAST_URI);
        if (lastPage == null || lastPage.isEmpty()) {
            result = new CommandResult(ProjectPages.MAIN_PAGE, true);
            return result;
        }
        result = new CommandResult(lastPage, false);
        return result;
    }
}
