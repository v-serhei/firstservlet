package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.LoginCommand;
import by.verbitsky.servletdemo.command.impl.LogoutCommand;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };

    Command command;

    public Command getCommand() {
        return command;
    }
}
