package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.ReviewFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.ContentServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NextPageCommand implements Command {
    private ContentService service = ContentServiceImpl.INSTANCE;
    private static final String TYPE_SONG = "song";
    private static final String TYPE_REVIEW = "review";
    private static final String TYPE_ALBUM = "album";
    private static final String TYPE_COMPILATION = "compilation";


    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String contentType = content.getRequestParameter(ParameterName.CONTENT_TYPE).toLowerCase();
        String pageNumber = content.getRequestParameter(ParameterName.PAGE_NUMBER);
        int requestedPageNumber = Integer.parseInt(pageNumber);
        switch (contentType) {
            case TYPE_SONG: {
                SongFilter filter;
                filter = (SongFilter) content.getSessionAttribute(AttributeName.SONG_FILTER);
                filter.setPageNumber(requestedPageNumber);
                return updateMainPageContent(filter, content);
            }
            case TYPE_REVIEW: {
                ReviewFilter filter;
                filter = (ReviewFilter) content.getSessionAttribute(AttributeName.REVIEW_FILTER);
                filter.setPageNumber(requestedPageNumber);
                return updateReviewPageContent(filter, content);
            }
            default: {
                throw new CommandException("NextPageCommand: unknown filter content type");
            }
        }
    }

    private CommandResult updateMainPageContent(SongFilter filter, SessionRequestContent content) throws CommandException {
        List<AudioContent> pageContent;
        try {
            pageContent = service.findFilteredContent(filter);
        } catch (ServiceException e) {
            throw new CommandException("NextPageCommand: error while receiving song content from db", e);
        }
        //content list
        content.addSessionAttribute(AttributeName.SONG_CONTENT, pageContent);
        return new CommandResult(PagePath.MAIN_PAGE, false);
    }

    private CommandResult updateReviewPageContent(ReviewFilter filter, SessionRequestContent content) throws CommandException {
        List<AudioContent> uniqueSongs;
        List<AudioContent> pageContent;
        try {
            filter.setFindUnique(true);
            uniqueSongs = service.findFilteredContent(filter);
            filter.setFindUnique(false);
            pageContent = service.findFilteredContent(filter);
        } catch (ServiceException e) {
            throw new CommandException("NextPageCommand: error while receiving song content from db", e);
        }
        //content list
        content.addSessionAttribute(AttributeName.REVIEW_CONTENT, pageContent);
        Set<String> songTitles = new HashSet<>();
        uniqueSongs.forEach((review) -> songTitles.add(((Review) review).getSongTitle()));
        //add additional content list (list of songs title)
        content.addSessionAttribute(AttributeName.ADDITIONAL_CONTENT_SONGS, songTitles);
        return new CommandResult(PagePath.REVIEW_PAGE, false);
    }
}