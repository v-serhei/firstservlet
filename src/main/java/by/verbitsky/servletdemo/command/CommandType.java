package by.verbitsky.servletdemo.command;

import by.verbitsky.servletdemo.command.impl.ChangeLanguageCommand;
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
    }, CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    };

    Command command;

    public Command getCommand() {
        return command;
    }
}
