package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.EmptyCommand;
import by.verbitsky.servletdemo.commandtype.WebCommandType;

public class CommandProvider {
    public static WebCommand defineCommand(String requestCommand) {
        if (requestCommand.isEmpty() || requestCommand == null) {
            return new EmptyCommand();
        }
        WebCommandType cmdType;
        WebCommand command;
        try {
            cmdType = WebCommandType.valueOf(requestCommand.toUpperCase());
            command = cmdType.getCommand();
        } catch (IllegalArgumentException exception) {
            //todo подумать как вернуть в реквест ошибку, возможно обернуть в Optional
            command = new EmptyCommand();
        }
        return command;
    }
}
