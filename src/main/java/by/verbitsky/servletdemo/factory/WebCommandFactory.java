package by.verbitsky.servletdemo.factory;

import by.verbitsky.servletdemo.command.WebCommand;
import by.verbitsky.servletdemo.command.impl.EmptyCommand;
import by.verbitsky.servletdemo.commandtype.WebCommandType;

public class WebCommandFactory {
    private static final String COMMAND = "command";

    public static WebCommand defineCommand(String requestCommand) {
        if (requestCommand.isEmpty() || requestCommand == null) {
            return new EmptyCommand();
        }
        WebCommandType cmdType;
        WebCommand command = null;
        try {
            cmdType = WebCommandType.valueOf(requestCommand.toUpperCase());
            command = cmdType.getCommand();
        } catch (IllegalArgumentException exception) {
            //todo подумать как вернуть в реквест ошибку
        }
        return command;
    }
}
