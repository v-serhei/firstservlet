package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.common.EmptyCommand;

/**
 * The type Command provider.
 * Provides method to define command type from user request query
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public class CommandProvider {
    private static final String COMMAND_REGEX = "(/.+/)";
    private static final String EMPTY_VALUE = "";

    /**
     * Define command method.
     * The command type is determined by the word after the last "/" symbol of user query line
     * If word doesn't match any type of {@link CommandType} enum values - returns empty command type
     *
     * @param requestCommand contains user query line from request
     * @return the command
     */
    public static Command defineCommand(String requestCommand) {
        if (requestCommand == null || requestCommand.isEmpty()) {
            return new EmptyCommand();
        }
        CommandType cmdType;
        Command command;
        try {
            cmdType = CommandType.valueOf(requestCommand.toUpperCase().replaceAll(COMMAND_REGEX, EMPTY_VALUE));
            command = cmdType.getCommand();
        } catch (IllegalArgumentException exception) {
            command = new EmptyCommand();
        }
        return command;
    }
}