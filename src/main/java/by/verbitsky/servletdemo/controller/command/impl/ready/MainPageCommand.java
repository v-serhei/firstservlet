package by.verbitsky.servletdemo.controller.command.impl.ready;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ContentType;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.ContentServiceImpl;

import java.util.List;

public class MainPageCommand implements Command {
    private static final String EMPTY_PARAMETER = "";
    private ContentService service = ContentServiceImpl.INSTANCE;


    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        List<AudioContent> pageContent;
        List<AudioContent> singers;
        List<AudioContent> genres;
        String songTitle = content.getRequestParameter(ParameterName.SONG);
        String singerName = content.getRequestParameter(ParameterName.SINGER);
        String genreName = content.getRequestParameter(ParameterName.GENRE);
        String enableFilter = content.getRequestParameter(ParameterName.RESET_FILTER);
        boolean isFiltered = Boolean.parseBoolean(enableFilter);
        SongFilter filter;
        if (isFiltered) {
            filter = new SongFilter(songTitle, genreName, singerName, AttributeValue.DEFAULT_PAGE_NUMBER);
        } else {
            filter = new SongFilter(EMPTY_PARAMETER, EMPTY_PARAMETER, EMPTY_PARAMETER, AttributeValue.DEFAULT_PAGE_NUMBER);
        }
        long totalSongCount;
        try {
            pageContent = service.findFilteredContent(filter);
            totalSongCount = service.calculateItemsCount(ContentType.SONG, filter);
            singers = service.findAllContent(ContentType.SINGER);
            genres = service.findAllContent(ContentType.GENRE);
        } catch (ServiceException e) {
            throw new CommandException("MainPageCommand: error while receiving song content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalSongCount * 1.0 / filter.getItemPerPage());
        //current page number
        content.addSessionAttribute(AttributeName.CONTENT_CURRENT_PAGE_NUMBER, AttributeValue.DEFAULT_PAGE_NUMBER);
        //total page count
        content.addSessionAttribute(AttributeName.CONTENT_TOTAL_PAGE_COUNT, totalPageCount);
        //content list
        content.addSessionAttribute(AttributeName.CONTENT, pageContent);
        //select tag authors
        content.addSessionAttribute(AttributeName.SINGER_LIST, singers);
        //select tag genres
        content.addSessionAttribute(AttributeName.GENRE_LIST, genres);
        //filter flag
        content.addSessionAttribute(AttributeName.CONTENT_FILTER, filter);
        //href value for pagination control
        content.addSessionAttribute(AttributeName.PAGINATION_CONTROLS_LINK, PagePath.PAGINATION_MAIN);
        if (isFiltered) {
            return new CommandResult(PagePath.MAIN_PAGE, false);
        } else {
            return new CommandResult(PagePath.MAIN_PAGE, true);
        }
    }
}
