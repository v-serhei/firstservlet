package by.verbitsky.servletdemo.controller.command.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.controller.command.Command;
import by.verbitsky.servletdemo.controller.command.CommandResult;
import by.verbitsky.servletdemo.controller.command.PagePath;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.ContentType;
import by.verbitsky.servletdemo.model.service.impl.ContentServiceImpl;

import java.util.List;

public class MainPageCommand implements Command {

    private static final int SONGS_PER_PAGE = 12;
    private static final int DEFAULT_CURRENT_PAGE = 1;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        List<AudioContent> pageContent;
        List<AudioContent> singers;
        List<AudioContent> genres;
        long totalSongCount;
        try {
            pageContent = ContentServiceImpl.INSTANCE.findSongs(SONGS_PER_PAGE, DEFAULT_CURRENT_PAGE);
            totalSongCount = ContentServiceImpl.INSTANCE.calculateItemsCount(ContentType.SONG);
            singers = ContentServiceImpl.INSTANCE.findAllContent(ContentType.SINGER);
            genres = ContentServiceImpl.INSTANCE.findAllContent(ContentType.GENRE);
        } catch (ServiceException e) {
            throw new CommandException("MainPageCommand: error while receiving song content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalSongCount * 1.0 / SONGS_PER_PAGE);
        //current page number
        content.addSessionAttribute(AttributeName.CONTENT_CURRENT_PAGE, DEFAULT_CURRENT_PAGE);
        //total page count
        content.addSessionAttribute(AttributeName.CONTENT_TOTAL_PAGE_COUNT, totalPageCount);
        //content list
        content.addSessionAttribute(AttributeName.CONTENT, pageContent);
        //select tag authors
        content.addSessionAttribute(AttributeName.SINGER_LIST, singers);
        //select tag genres
        content.addSessionAttribute(AttributeName.GENRE_LIST, genres);
        //filter flag
        content.addSessionAttribute(AttributeName.CONTENT_FILTER, false);
        //href value for pagination control
        content.addSessionAttribute(AttributeName.PAGINATION_CONTROLS_LINK, PagePath.PAGINATION_MAIN);

        return new CommandResult(PagePath.MAIN_PAGE, true);
    }
}
