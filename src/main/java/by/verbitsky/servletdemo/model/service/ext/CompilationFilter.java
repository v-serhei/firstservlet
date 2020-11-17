package by.verbitsky.servletdemo.model.service.ext;

import by.verbitsky.servletdemo.controller.command.AttributeValue;
import by.verbitsky.servletdemo.entity.ContentType;
import by.verbitsky.servletdemo.model.service.ContentFilter;

public class CompilationFilter extends ContentFilter {
    private static final String EMPTY_PARAMETER = "";
    private String compilationTitle;
    private String compilationType;

    public CompilationFilter() {
        super(ContentType.COMPILATION, AttributeValue.DEFAULT_PAGE_NUMBER, AttributeValue.COMPILATION_PAGE_REVIEW_PER_PAGE);
        setCompilationTitle(EMPTY_PARAMETER);
        setCompilationType(EMPTY_PARAMETER);
    }

    public String getCompilationTitle() {
        return compilationTitle;
    }

    public void setCompilationTitle(String compilationTitle) {
        if (compilationTitle != null) {
            this.compilationTitle = compilationTitle;
        }
    }

    public String getCompilationType() {
        return compilationType;
    }

    public void setCompilationType(String compilationType) {
        if (compilationType != null){
            this.compilationType = compilationType;
        }
    }
}
