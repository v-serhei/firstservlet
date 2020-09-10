package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.EmptyCommand;

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
            //todo подумать как вернуть в реквест ошибку, возможно обернуть в Optional
            command = new EmptyCommand();
        }
        return command;
    }
}