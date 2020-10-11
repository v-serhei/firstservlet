package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.LoginCommand;
import by.verbitsky.servletdemo.command.impl.RegisterCommand;
import by.verbitsky.servletdemo.command.impl.SwitchLanguageCommand;

public enum CommandType {
    LOGIN (new LoginCommand()) ,
    LOGOUT (new LoginCommand()),
    REGISTER (new RegisterCommand()),
    RU (new SwitchLanguageCommand()),
    EN (new SwitchLanguageCommand())


    ;


    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
