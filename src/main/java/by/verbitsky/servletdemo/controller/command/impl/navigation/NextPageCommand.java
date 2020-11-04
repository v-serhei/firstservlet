package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ext.Compilation;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.CompilationFilter;
import by.verbitsky.servletdemo.model.service.ext.ReviewFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NextPageCommand implements Command {
    private ContentService service = AudioContentService.INSTANCE;
    private static final String TYPE_SONG = "song";
    private static final String TYPE_REVIEW = "review";
    private static final String TYPE_COMPILATION = "compilation";


    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String contentType = content.getRequestParameter(ParameterName.CONTENT_TYPE).toLowerCase();
        String pageNumber = content.getRequestParameter(ParameterName.PAGE_NUMBER);
        int requestedPageNumber = Integer.parseInt(pageNumber);
        switch (contentType) {
            case TYPE_SONG: {
                SongFilter filter = (SongFilter) content.getSessionAttribute(AttributeName.SONG_FILTER);
                filter.setPageNumber(requestedPageNumber);
                return updateMainPageContent(filter, content);
            }
            case TYPE_REVIEW: {
                ReviewFilter filter = (ReviewFilter) content.getSessionAttribute(AttributeName.REVIEW_FILTER);
                filter.setPageNumber(requestedPageNumber);
                return updateReviewPageContent(filter, content);
            }
            case TYPE_COMPILATION: {
                CompilationFilter filter = (CompilationFilter) content.getSessionAttribute(AttributeName.COMPILATION_FILTER);
                filter.setPageNumber(requestedPageNumber);
                return updateCompilationPageContent(filter, content);
            }
            default: {
                throw new CommandException("NextPageCommand: unknown filter content type");
            }
        }
    }

    private CommandResult updateCompilationPageContent(CompilationFilter filter, SessionRequestContent content) throws CommandException {
        List<AudioContent> pageContent;
        try {
            pageContent = service.findFilteredContent(filter);
            for (AudioContent item : pageContent) {
                SongFilter songFilter = new SongFilter();
                songFilter.setCompilationId(item.getId());
                ((Compilation)item).addAllSongs(service.findFilteredContent(songFilter));
            }
        } catch (ServiceException e) {
            throw new CommandException("CompilationPageCommand: error while receiving compilation content from db", e);
        }
        content.addSessionAttribute(AttributeName.COMPILATION_CONTENT, pageContent);
        return new CommandResult(PagePath.FORWARD_COMPILATION_PAGE, false);
    }

    private CommandResult updateMainPageContent(SongFilter filter, SessionRequestContent content) throws CommandException {
        List<AudioContent> pageContent;
        try {
            pageContent = service.findFilteredContent(filter);
        } catch (ServiceException e) {
            throw new CommandException("NextPageCommand: error while receiving song content from db", e);
        }
        content.addSessionAttribute(AttributeName.SONG_CONTENT, pageContent);
        return new CommandResult(PagePath.FORWARD_MAIN_PAGE, false);
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
        content.addSessionAttribute(AttributeName.REVIEW_CONTENT, pageContent);
        Set<String> songTitles = new HashSet<>();
        uniqueSongs.forEach((review) -> songTitles.add(((Review) review).getSongTitle()));
        content.addSessionAttribute(AttributeName.ADDITIONAL_CONTENT_SONGS, songTitles);
        return new CommandResult(PagePath.FORWARD_REVIEW_PAGE, false);
    }
}