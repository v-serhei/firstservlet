package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.CommandResult;
import by.verbitsky.servletdemo.command.EmptyResult;
import by.verbitsky.servletdemo.util.WebResourcesManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    private static final String MAIN_PAGE_PARAMETER_NAME = "pages.jsp.main";
    private static final String RESULT_PAGE_ATTRIBUTE_NAME = "resultPage";
    @Override
    public CommandResult execute(HttpServletRequest request) {
        String jsp = WebResourcesManager.getInstance().getProperty(MAIN_PAGE_PARAMETER_NAME);
        CommandResult result = new EmptyResult();
        result.addAttribute(RESULT_PAGE_ATTRIBUTE_NAME, jsp);
        return result;
    }
}
