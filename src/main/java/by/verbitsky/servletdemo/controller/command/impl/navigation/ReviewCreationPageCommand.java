package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.util.SimpleParser;

public class ReviewCreationPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
        if (!user.getLoginStatus()) {
            return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
        }
        String reviewSongTitle = content.getRequestParameter(ParameterName.SONG);
        String songTitle = SimpleParser.parseSongTitleFromReview(reviewSongTitle);
        String author = SimpleParser.parseAuthorNameFromReview(reviewSongTitle);
        content.addSessionAttribute(AttributeName.REVIEW_CREATION_SONG, songTitle);
        content.addSessionAttribute(AttributeName.REVIEW_CREATION_SINGER, author);
        return new CommandResult(PagePath.FORWARD_ADD_REVIEW_PAGE,  false);
    }
}
