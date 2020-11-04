package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.common.EmptyCommand;

public class CommandProvider {
    private static final String COMMAND_REGEX = "(/.+/)";
    private static final String EMPTY_VALUE = "";

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
