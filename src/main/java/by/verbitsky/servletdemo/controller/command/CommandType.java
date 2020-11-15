package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.admin.*;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.*;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.*;
import by.verbitsky.servletdemo.controller.command.impl.common.*;
import by.verbitsky.servletdemo.controller.command.impl.navigation.*;
import by.verbitsky.servletdemo.controller.command.impl.user.*;

public enum CommandType {
    //common navigation
    MAIN(new MainPageCommand()),
    LOGIN(new LoginPageCommand()),
    REGISTRATION(new RegistrationPageCommand()),
    REVIEWS(new ReviewPageCommand()),
    COMPILATIONS(new CompilationPageCommand()),
    NEXT_PAGE(new NextPageCommand()),
    OPERATION_RESULT(new OperationResultCommand()),
    ORDER(new OrderPageCommand()),
    USER_ORDERS(new UserOrdersCommand()),
    USER_REVIEWS(new UserReviewsPageCommand()),

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
    DELETE_REVIEW(new DeleteReviewCommand()),

    //user commands
    BASKET_ADD(new BasketAddCommand()),
    BASKET_REMOVE(new BasketRemoveCommand()),
    SONG_REMOVE(new OrderRemoveSongCommand()),
    PAY_ORDER(new PayOrderCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    OPEN_ORDER(new OpenOrderCommand()),
    REMOVE_ORDER(new RemoveOrderCommand()),
    SETTINGS(new SettingsPageCommand()),

    //admin navigation
    USER_MANAGEMENT(new UserManagementPageCommand()),
    REVIEW_MANAGEMENT(new ReviewManagementPageCommand()),
    SONG_MANAGEMENT(new SongManagementPageCommand()),
    SINGER_MANAGEMENT(new SingerManagementPageCommand()),
    GENRE_MANAGEMENT(new GenreManagementPageCommand()),
    ALBUM_MANAGEMENT(new AlbumManagementPageCommand()),
    COMPILATION_MANAGEMENT(new CompilationManagementPageCommand()),


    UPLOAD_ERROR(new UploadErrorPageCommand()),

    //admin commands,
    UPDATE_USER(new AdminUpdateUserCommand()),
    DELETE_USER_REVIEW(new AdminDeleteReviewCommand()),
    UPDATE_SINGER (new AdminUpdateSingerCommand()),
    CREATE_SINGER(new AdminCreateSingerCommand()),
    UPDATE_GENRE (new AdminUpdateGenreCommand()),
    CREATE_GENRE(new AdminCreateGenreCommand()),
    UPDATE_ALBUM (new AdminUpdateAlbumCommand()),
    CREATE_ALBUM(new AdminCreateAlbumCommand()),
    CREATE_COMPILATION(new AdminCreateCompilationCommand()),
    COMPILATION(new AdminCompilationPageCommand()),
    DELETE_COMPILATION(new AdminDeleteCompilationCommand()),


    CREATE_SONG(new AdminCreateSongCommand()),
    UPDATE_SONG(new AdminUpdateSongCommand()),





    END_OFF_COMMANDS(new UserManagementPageCommand());



    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCommand() {
        return command;
    }
}
