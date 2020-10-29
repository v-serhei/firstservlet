package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.*;
import by.verbitsky.servletdemo.controller.command.impl.common.*;
import by.verbitsky.servletdemo.controller.command.impl.nav.*;

public enum CommandType {

    //Common commands
    BASKET_ADD (new BasketAddCommand()),
    BASKET_REMOVE (new BasketRemoveCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),


    //page nav commands
    ADMIN_PAGE(new AdminPageCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    MAIN_PAGE(new MainPageCommand()),
    NEXT_SONG_PAGE (new NextPageCommand()),

    ORDER_PAGE (new OrderPageCommand()),//todo

    PROFILE_PAGE(new ProfilePageCommand()),
    REGISTER_PAGE(new RegistrationPageCommand()),

    REVIEW_PAGE(new ReviewPageCommand()), //todo

    SEARCH_SONG (new MainPageCommand()),

    //admin commands





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
