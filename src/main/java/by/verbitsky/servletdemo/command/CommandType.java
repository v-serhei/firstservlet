package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.SwitchLanguageCommand;
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
    }, RU {
        {
            this.command = new SwitchLanguageCommand();
        }
    }, EN {
        {
            this.command = new SwitchLanguageCommand();
        }
    };
    Command command;

    public Command getCommand() {
        return command;
    }
}
