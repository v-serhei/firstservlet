package by.verbitsky.servletdemo.controller.command.impl.common;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.ParameterName;
import by.verbitsky.servletdemo.controller.command.impl.navigation.CompilationPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.navigation.MainPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.navigation.ReviewPageCommand;
import by.verbitsky.servletdemo.exception.CommandException;

public class SearchCommand implements Command {

    private static final String SONG = "song";
    private static final String COMPILATION = "compilation";
    private static final String REVIEW = "review";

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String searchedContentType = content.getRequestParameter(ParameterName.CONTENT_TYPE);
        if (searchedContentType == null || searchedContentType.isEmpty()) {
            throw new CommandException("Next page command: received null or empty content type value");
        }
        switch (searchedContentType.toLowerCase()) {
            case SONG: {
                return new MainPageCommand().execute(content);
            }
            case COMPILATION: {
                return new CompilationPageCommand().execute(content);
            }
            case REVIEW: {
                return new ReviewPageCommand().execute(content);
            }
        }
        throw new CommandException("Next page command: received unknown content type value");
    }
}
