package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.AdminPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.common.*;
import by.verbitsky.servletdemo.controller.command.impl.navigation.*;
import by.verbitsky.servletdemo.controller.command.impl.user.*;

public enum CommandType {

    //navigation
    MAIN(new MainPageCommand()),
    LOGIN(new LoginPageCommand()),
    REGISTRATION(new RegistrationPageCommand()),
    ADMIN(new AdminPageCommand()),
    REVIEWS(new ReviewPageCommand()),
    COMPILATIONS(new CompilationPageCommand()),
    NEXT_PAGE(new NextPageCommand()),
    OPERATION_RESULT(new OperationResultCommand()),
    ORDER(new OrderPageCommand()),
    USER_ORDERS(new UserOrdersCommand()),

    //common commands
    PROCESS_LOGIN(new LoginCommand()),
    PROCESS_LOGOUT(new LogoutCommand()),
    PROCESS_REGISTRATION(new RegisterCommand()),
    SEARCH(new SearchCommand()),
    RU(new LanguageCommand()),
    EN(new LanguageCommand()),
    UPDATE_EMAIL(new UpdateEmailCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    ADD_REVIEW(new ReviewCreationPageCommand()),
    CREATE_REVIEW(new CreateReviewCommand()),


    //user commands
    BASKET_ADD(new BasketAddCommand()),
    BASKET_REMOVE(new BasketRemoveCommand()),
    SONG_REMOVE(new OrderRemoveSongCommand()),
    PAY_ORDER(new PayOrderCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    OPEN_ORDER(new OpenOrderCommand()),
    REMOVE_ORDER(new RemoveOrderCommand()),
    SETTINGS(new SettingsPageCommand());



    //admin commands




    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
