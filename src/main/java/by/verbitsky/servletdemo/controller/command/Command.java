package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.exception.CommandException;

/**
 * Common interface for processing user request.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public interface Command {
    /**
     * Execute command result.
     *
     * @param content: Contains HttpServletRequest and HttpSession objects
     *                 which are needed to get incoming request parameters and
     *                 write attributes for server response
     * @return the command result
     * @throws CommandException: Exception throws when received an exception from service module
     *                           (such as validation exceptions from service layer or Dao exceptions)
     * @see CommandResult
     * @see SessionRequestContent
     */
    CommandResult execute(SessionRequestContent content) throws CommandException;
}
