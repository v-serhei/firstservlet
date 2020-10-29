package by.verbitsky.servletdemo.controller.command.impl.nav;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.ContentServiceImpl;

import java.util.List;

public class NextPageCommand implements Command {
    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        SongFilter filter = (SongFilter) content.getSessionAttribute(AttributeName.CONTENT_FILTER);
        List<AudioContent> pageContent;
        String pageNumber = content.getRequestParameter(ParameterName.PAGE_NUMBER);
        int   requestedPageNumber = Integer.parseInt(pageNumber);
        filter.setPageNumber(requestedPageNumber);
        try {
            pageContent = ContentServiceImpl.INSTANCE.findFilteredContent(filter);
        } catch ( ServiceException e) {
            throw new CommandException("NextPageCommand: error while receiving song content from db", e);
        }
        //current page number
        content.addSessionAttribute(AttributeName.CONTENT_CURRENT_PAGE_NUMBER, requestedPageNumber);
        //content list
        content.addSessionAttribute(AttributeName.CONTENT, pageContent);
        return new CommandResult((String) content.getSessionAttribute(AttributeName.SESSION_LAST_URI), false);
    }
}