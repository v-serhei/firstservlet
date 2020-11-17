package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.ext.Review;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.ReviewFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReviewPageCommand implements Command {
    private ContentService service = AudioContentService.INSTANCE;
    private long reviewCount;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String songTitle = content.getRequestParameter(ParameterName.SONG);
        String singer = content.getRequestParameter(ParameterName.SINGER);
        String enableFilter = content.getRequestParameter(ParameterName.ENABLE_FILTER);
        boolean isFiltered = Boolean.parseBoolean(enableFilter);
        ReviewFilter filter;
        if (isFiltered) {
            filter = (ReviewFilter) content.getSessionAttribute(AttributeName.REVIEW_FILTER);
        } else {
            filter = new ReviewFilter();
        }
        fillReviewFilter(filter, songTitle);
        generateContent(filter, content);
        if (reviewCount > 0) {
            return new CommandResult(PagePath.FORWARD_REVIEW_PAGE, false);
        } else {
            try {
                SongFilter songFilter = new SongFilter();
                songFilter.setSongTitle(songTitle);
                songFilter.setSingerName(singer);
                List<AudioContent> filteredContent = AudioContentService.INSTANCE.findFilteredContent(songFilter);
                if (filteredContent.size() > 0) {
                    content.addSessionAttribute(AttributeName.REVIEW_CREATION_SONG, songTitle);
                    content.addSessionAttribute(AttributeName.REVIEW_CREATION_SINGER, singer);
                } else {
                    return new CommandResult(PagePath.FORWARD_REVIEW_PAGE, false);
                }
            } catch (ServiceException e) {
                throw new CommandException("ReviewPageCommand: error while searching content", e);
            }
            User user = (User) content.getSessionAttribute(AttributeName.SESSION_USER);
            if (user.getLoginStatus()) {
                return new CommandResult(PagePath.FORWARD_ADD_REVIEW_PAGE, false);
            } else {
                return new CommandResult(PagePath.REDIRECT_LOGIN_PAGE, true);
            }
        }
    }

    private void fillReviewFilter(ReviewFilter filter, String songTitle) {
        filter.setSongTitle(songTitle);
        filter.setPageNumber(AttributeValue.DEFAULT_PAGE_NUMBER);
    }

    private void generateContent(ReviewFilter filter, SessionRequestContent content) throws CommandException {
        long songsCount;
        List<AudioContent> uniqueSongs;
        List<AudioContent> pageContent;
        try {
            songsCount = service.calculateItemsCount(filter);
            filter.setFindUnique(true);
            uniqueSongs = service.findFilteredContent(filter);
            filter.setFindUnique(false);
            pageContent = service.findFilteredContent(filter);
        } catch (ServiceException e) {
            throw new CommandException("ReviewPageCommand: error while receiving review content from db", e);
        }
        int totalPageCount = (int) Math.ceil(songsCount * 1.0 / filter.getItemPerPage());
        //add total page count
        content.addSessionAttribute(AttributeName.REVIEW_TOTAL_PAGE_COUNT, totalPageCount);
        //add content list
        content.addSessionAttribute(AttributeName.REVIEW_CONTENT, pageContent);
        Set<String> songTitles = new HashSet<>();
        uniqueSongs.forEach((review) -> songTitles.add(((Review) review).getSongTitle()));
        //add additional content list (list of songs title)
        content.addSessionAttribute(AttributeName.ADDITIONAL_CONTENT_SONGS, songTitles);
        //add result count:
        content.addSessionAttribute(AttributeName.REVIEW_SEARCH_COUNT_RESULT, pageContent.size());
        //add filter value
        content.addSessionAttribute(AttributeName.REVIEW_FILTER, filter);
        //add link for pagination controls
        content.addSessionAttribute(AttributeName.REVIEW_CONTROLS_LINK, PagePath.PAGINATION_REVIEW);
        reviewCount = pageContent.size();
    }
}
