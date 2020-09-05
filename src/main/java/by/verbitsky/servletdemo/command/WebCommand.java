package by.verbitsky.servletdemo.command;

import javax.servlet.http.HttpServletRequest;

public interface WebCommand {
    String execute(HttpServletRequest request);
}
