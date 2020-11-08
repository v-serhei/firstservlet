package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.AdminPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.ProfilePageCommand;
import by.verbitsky.servletdemo.controller.command.impl.common.*;
import by.verbitsky.servletdemo.controller.command.impl.navigation.*;
import by.verbitsky.servletdemo.controller.command.impl.user.*;

public enum CommandType {

    //navigation
    MAIN(new MainPageCommand()),
    LOGIN(new LoginPageCommand()),
    REGISTRATION(new RegistrationPageCommand()),
    ADMIN(new AdminPageCommand()),
    PROFILE(new ProfilePageCommand()),
    REVIEWS(new ReviewPageCommand()),
    COMPILATIONS(new CompilationPageCommand()),
    NEXT_PAGE(new NextPageCommand()),
    OPERATION_RESULT(new OperationResultCommand()),
    ORDER(new OrderPageCommand()),

    //common commands
    PROCESS_LOGIN(new LoginCommand()),
    PROCESS_LOGOUT(new LogoutCommand()),
    PROCESS_REGISTRATION(new RegisterCommand()),
    SEARCH(new SearchCommand()),
    RU(new LanguageCommand()),
    EN(new LanguageCommand()),



    //user commands
    BASKET_ADD(new BasketAddCommand()),
    BASKET_REMOVE(new BasketRemoveCommand()),
    SONG_REMOVE(new OrderRemoveSongCommand()),
    PAY_ORDER(new PayOrderCommand()),
    CREATE_ORDER(new CreateOrderCommand());


    //admin commands




    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
