package by.verbitsky.servletdemo.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    CommandResult execute(HttpServletRequest request);
}
