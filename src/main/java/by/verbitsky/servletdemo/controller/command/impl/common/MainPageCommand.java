package by.verbitsky.servletdemo.controller.command.impl.common;

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
    private ContentService service = ContentServiceImpl.INSTANCE;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String songTitle = content.getRequestParameter(ParameterName.SONG);
        String singerName = content.getRequestParameter(ParameterName.SINGER);
        String genreName = content.getRequestParameter(ParameterName.GENRE);
        String enableFilter = content.getRequestParameter(ParameterName.RESET_FILTER);
        boolean isFiltered = Boolean.parseBoolean(enableFilter);
        SongFilter filter;
        if (isFiltered) {
            filter = (SongFilter) content.getSessionAttribute(AttributeName.CONTENT_FILTER);
            filter.setSongTitle(songTitle);
            filter.setSongGenre(genreName);
            filter.setSingerName(singerName);
            filter.setPageNumber(AttributeValue.DEFAULT_PAGE_NUMBER);
        } else {
            filter = new SongFilter();
        }
        //update filter attribute
        content.addSessionAttribute(AttributeName.CONTENT_FILTER, filter);
        if (isFiltered) {
            return updateFilteredContent (filter, content);
        }else {
            return generateFilteredContent (filter, content);
        }
    }

    private CommandResult generateFilteredContent(SongFilter filter, SessionRequestContent content) throws CommandException {
        long totalSongCount;
        List<AudioContent> pageContent;
        List<AudioContent> singers;
        List<AudioContent> genres;
        try {
            pageContent = service.findFilteredContent(filter);
            totalSongCount = service.calculateItemsCount(ContentType.SONG, filter);
            singers = service.findAllContent(ContentType.SINGER);
            genres = service.findAllContent(ContentType.GENRE);
        } catch (ServiceException e) {
            throw new CommandException("MainPageCommand: error while receiving song content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalSongCount * 1.0 / filter.getItemPerPage());
        //add total page count
        content.addSessionAttribute(AttributeName.CONTENT_TOTAL_PAGE_COUNT, totalPageCount);
        //add content list
        content.addSessionAttribute(AttributeName.CONTENT, pageContent);
        //add select tag authors
        content.addSessionAttribute(AttributeName.SINGER_LIST, singers);
        //add select tag genres
        content.addSessionAttribute(AttributeName.GENRE_LIST, genres);
        //add href value for pagination control
        content.addSessionAttribute(AttributeName.PAGINATION_CONTROLS_LINK, PagePath.PAGINATION_MAIN);
        return new CommandResult(PagePath.MAIN_PAGE, false);
    }

    private CommandResult updateFilteredContent(SongFilter filter, SessionRequestContent content) throws CommandException {
        long totalSongCount;
        List<AudioContent> pageContent;
        try {
            pageContent = service.findFilteredContent(filter);
            totalSongCount = service.calculateItemsCount(ContentType.SONG, filter);
        } catch (ServiceException e) {
            throw new CommandException("MainPageCommand: error while receiving song content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalSongCount * 1.0 / filter.getItemPerPage());
        //total page count
        content.addSessionAttribute(AttributeName.CONTENT_TOTAL_PAGE_COUNT, totalPageCount);
        //update content list
        content.addSessionAttribute(AttributeName.CONTENT, pageContent);
        return new CommandResult(PagePath.MAIN_PAGE, true);
    }
}