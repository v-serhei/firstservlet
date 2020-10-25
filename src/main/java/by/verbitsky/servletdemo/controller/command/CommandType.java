package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.*;
import by.verbitsky.servletdemo.controller.command.impl.common.*;

public enum CommandType {
    /**
     * user executable command (method post)
     */
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),

    /**
     * user redirected command (method get)
     */
    LOGIN_PAGE(new LoginPageCommand()),
    MAIN_PAGE(new MainPageCommand()),
    REGISTER_PAGE(new RegistrationPageCommand()),
    PROFILE_PAGE(new ProfilePageCommand()),
    ADMIN_PAGE(new AdminPageCommand()),
    SEARCH_SONG (new MainPageCommand()),
    NEXT_SONG_PAGE (new NextPageCommand()),

    /**
     * switch lang
     */
    RU(new LanguageCommand()),
    EN(new LanguageCommand());


    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
