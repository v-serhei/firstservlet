package by.verbitsky.servletdemo.commandtype;

import by.verbitsky.servletdemo.command.WebCommand;
import by.verbitsky.servletdemo.command.impl.LoginCommand;
import by.verbitsky.servletdemo.command.impl.LogoutCommand;

public enum WebCommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };

    WebCommand command;

    public WebCommand getCommand() {
        return command;
    }
}
