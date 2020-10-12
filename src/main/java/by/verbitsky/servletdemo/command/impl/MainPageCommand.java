package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.projectconst.AttributesNames;
import by.verbitsky.servletdemo.projectconst.ProjectPages;

public class MainPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) {
        CommandResult result = new CommandResult(ProjectPages.MAIN_PAGE, false);
        content.addSessionAttribute(AttributesNames.SESSION_ATTR_LAST_COMMAND, this);
        //todo тут сделать пагинацию
        return result;
    }
}
