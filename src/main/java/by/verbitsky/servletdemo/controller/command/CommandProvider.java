package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.common.EmptyCommand;

public class CommandProvider {
    public static Command defineCommand(String requestCommand) {
        if (requestCommand == null || requestCommand.isEmpty()) {
            return new EmptyCommand();
        }
        CommandType cmdType;
        Command command;
        try {
            cmdType = CommandType.valueOf(requestCommand.toUpperCase());
            command = cmdType.getCommand();
        } catch (IllegalArgumentException exception) {
            command = new EmptyCommand();
        }
        return command;
    }
}
