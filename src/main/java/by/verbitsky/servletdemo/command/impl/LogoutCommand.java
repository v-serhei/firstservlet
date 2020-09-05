package by.verbitsky.servletdemo.command.impl;

import by.verbitsky.servletdemo.command.WebCommand;
import by.verbitsky.servletdemo.service.AuthService;
import by.verbitsky.servletdemo.service.impl.UserService;

public class LogoutCommand implements WebCommand {

    private AuthService service = new UserService ();
    @Override
    public void execute() {
        //todo release this
        service.logout("");
    }
}
