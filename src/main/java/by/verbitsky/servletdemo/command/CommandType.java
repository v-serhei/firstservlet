package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.LoginCommand;
import by.verbitsky.servletdemo.command.impl.LogoutCommand;
import by.verbitsky.servletdemo.command.impl.RegisterCommand;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    }, REGISTER {
        {
            this.command = new RegisterCommand();
        }
    };

    Command command;

    public Command getCommand() {
        return command;
    }
}
