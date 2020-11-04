package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.*;
import by.verbitsky.servletdemo.controller.command.impl.common.*;
import by.verbitsky.servletdemo.controller.command.impl.navigation.*;

public enum CommandType {
/*
    //Common commands
    BASKET_ADD (new BasketAddCommand()),
    BASKET_REMOVE (new BasketRemoveCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand()),
    SEARCH_REVIEW (new ReviewPageCommand()),
    SEARCH_SONG (new MainPageCommand()),
    SEARCH_COMPILATION (new CompilationPageCommand()),

    //page nav commands
    ADMIN_PAGE(new AdminPageCommand()),
    LOGIN_PAGE(new LoginPageCommand()),
    MAIN_PAGE(new MainPageCommand()),
    NEXT_PAGE(new NextPageCommand()),

    ORDER_PAGE (new OrderPageCommand()),//todo

    PROFILE_PAGE(new ProfilePageCommand()),
    REGISTER_PAGE(new RegistrationPageCommand()),
    REVIEW_PAGE(new ReviewPageCommand()),
    COMPILATION_PAGE(new CompilationPageCommand()),
    */

    //navigation
    MAIN(new MainPageCommand()),
    LOGIN(new LoginPageCommand()),
    REGISTRATION(new RegistrationPageCommand()),
    ADMIN(new AdminPageCommand()),
    PROFILE(new ProfilePageCommand()),
    REVIEWS(new ReviewPageCommand()),
    COMPILATIONS(new CompilationPageCommand()),
    NEXT_PAGE(new NextPageCommand()),



    //processing commands
    PROCESS_LOGIN(new LoginCommand()),
    PROCESS_LOGOUT(new LogoutCommand()),
    PROCESS_REGISTRATION(new RegisterCommand()),
    SEARCH(new SearchCommand()),
    RU(new LanguageCommand()),
    EN(new LanguageCommand()),


    //need fix:
    BASKET_ADD(new BasketAddCommand()),
    BASKET_REMOVE(new BasketRemoveCommand()),


    ORDER_PAGE(new OrderPageCommand());//todo




    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
