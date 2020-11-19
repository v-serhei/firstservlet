package by.verbitsky.servletdemo.exception;

/**
 * Class Command exception
 * CommandException object could be thrown by Command execute method
 * <p>
 *
 * @see by.verbitsky.servletdemo.controller.command.Command
 */
@SuppressWarnings("serial")
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}