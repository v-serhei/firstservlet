package by.verbitsky.servletdemo.controller.command.impl.navigation;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.*;
import by.verbitsky.servletdemo.entity.AudioContent;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.entity.ext.Compilation;
import by.verbitsky.servletdemo.exception.CommandException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.service.ContentService;
import by.verbitsky.servletdemo.model.service.ext.CompilationFilter;
import by.verbitsky.servletdemo.model.service.ext.SongFilter;
import by.verbitsky.servletdemo.model.service.impl.AudioContentService;

import java.util.List;

public class CompilationPageCommand implements Command {
    private ContentService service = AudioContentService.INSTANCE;

    @Override
    public CommandResult execute(SessionRequestContent content) throws CommandException {
        String compilationTitle = content.getRequestParameter(ParameterName.COMPILATION);
        String compilationType = content.getRequestParameter(ParameterName.COMPILATION_TYPE_NAME);
        String enableFilter = content.getRequestParameter(ParameterName.ENABLE_FILTER);
        boolean isFiltered = Boolean.parseBoolean(enableFilter);
        CompilationFilter filter;
        if (isFiltered) {
            filter = (CompilationFilter) content.getSessionAttribute(AttributeName.COMPILATION_FILTER);
        } else {
            filter = new CompilationFilter();

        }
        fillCompilationFilter(filter, compilationTitle, compilationType);
        return generateFilteredContent(filter, content);
    }

    private void fillCompilationFilter(CompilationFilter filter, String compilationTitle, String compilationType) {
        filter.setCompilationTitle(compilationTitle);
        filter.setCompilationType(compilationType);
        filter.setPageNumber(AttributeValue.DEFAULT_PAGE_NUMBER);
    }

    private CommandResult generateFilteredContent(CompilationFilter filter, SessionRequestContent content) throws CommandException {
        long totalContentCount;
        List<AudioContent> pageContent;
        List<String> compilationTypes;
        try {
            totalContentCount = service.calculateItemsCount(filter);
            pageContent = service.findFilteredContent(filter);
            compilationTypes = service.findContentProperties(ContentType.COMPILATION);
            for (AudioContent item : pageContent) {
                SongFilter songFilter = new SongFilter();
                songFilter.setCompilationId(item.getId());
                songFilter.setItemPerPage(AttributeValue.MAX_COMPILATION_SONG_VALUE);
                ((Compilation)item).addAllSongs(service.findFilteredContent(songFilter));
            }
        } catch (ServiceException e) {
            throw new CommandException("CompilationPageCommand: error while receiving compilation content from db", e);
        }
        int totalPageCount = (int) Math.ceil(totalContentCount * 1.0 / filter.getItemPerPage());
        //add total page count
        content.addSessionAttribute(AttributeName.COMPILATION_TOTAL_PAGE_COUNT, totalPageCount);
        //add content list
        content.addSessionAttribute(AttributeName.COMPILATION_CONTENT, pageContent);
        //add select tag compilation types
        content.addSessionAttribute(AttributeName.COMPILATION_TYPES_LIST, compilationTypes);
        //add href value for pagination control
        content.addSessionAttribute(AttributeName.COMPILATION_CONTROLS_LINK, PagePath.PAGINATION_COMPILATION);
        //add result count:
        content.addSessionAttribute(AttributeName.COMPILATION_SEARCH_COUNT_RESULT, totalContentCount);
        //add filter value
        content.addSessionAttribute(AttributeName.COMPILATION_FILTER, filter);
        return new CommandResult(PagePath.FORWARD_COMPILATION_PAGE, false);
    }
}