package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;

public class MainPageCommand implements Command {
    private ContentService service = AudioContentService.INSTANCE;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String songTitle = content.getRequestParameter(ParameterName.SONG);
        String singerName = content.getRequestParameter(ParameterName.SINGER);
        String genreName = content.getRequestParameter(ParameterName.GENRE);
        String albumTitle = content.getRequestParameter(ParameterName.ALBUM);
        String enableFilter = content.getRequestParameter(ParameterName.ENABLE_FILTER);
        boolean isFiltered = Boolean.parseBoolean(enableFilter);
        SongFilter filter;
        if (isFiltered) {
            filter = (SongFilter) content.getSessionAttribute(AttributeName.SONG_FILTER);
        } else {
            filter = new SongFilter();
        }
        fillSongFilter(filter, songTitle, genreName, singerName, albumTitle);
        return generateFilteredContent(filter, content);
    }

    private void fillSongFilter(SongFilter filter, String songTitle, String genreName, String singerName, String albumTitle) {
        filter.setSongTitle(songTitle);
        filter.setSongGenre(genreName);
        filter.setSingerName(singerName);
        filter.setAlbumTitle(albumTitle);
        filter.setPageNumber(AttributeValue.DEFAULT_PAGE_NUMBER);
    }

    private CommandResult generateFilteredContent(SongFilter filter, SessionRequestContent content) throws CommandException {
        long totalContentCount;
        List<AudioContent> pageContent;
        List<AudioContent> singers;
        List<AudioContent> genres;
        try {
            pageContent = service.findFilteredContent(filter);
            totalContentCount = service.calculateItemsCount(filter);
            singers = service.findAllContent(ContentType.SINGER);
            genres = service.findAllContent(ContentType.GENRE);
        } catch (ServiceException e) {
            throw new CommandException("MainPageCommand: error while receiving song content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalContentCount * 1.0 / filter.getItemPerPage());
        //add total page count
        content.addSessionAttribute(AttributeName.SONG_TOTAL_PAGE_COUNT, totalPageCount);
        //add content list
        content.addSessionAttribute(AttributeName.SONG_CONTENT, pageContent);
        //add select tag authors
        content.addSessionAttribute(AttributeName.SINGER_LIST, singers);
        //add select tag genres
        content.addSessionAttribute(AttributeName.GENRE_LIST, genres);
        //add href value for pagination control
        content.addSessionAttribute(AttributeName.SONG_CONTROLS_LINK, PagePath.PAGINATION_MAIN);
        //add result count:
        content.addSessionAttribute(AttributeName.SONG_SEARCH_COUNT_RESULT, totalContentCount);
        //add filter value
        content.addSessionAttribute(AttributeName.SONG_FILTER, filter);
        return new CommandResult(PagePath.FORWARD_MAIN_PAGE, false);
    }
}