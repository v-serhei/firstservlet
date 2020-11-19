package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.admin.*;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.AdminCompilationPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.AlbumManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.CompilationManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.GenreManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.SongManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ReviewManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.SingerManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.UserManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.common.BasketAddCommand;
import by.verbitsky.servletdemo.controller.command.impl.common.BasketRemoveCommand;
import by.verbitsky.servletdemo.controller.command.impl.user.*;
import by.verbitsky.servletdemo.entity.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Command permission validator. Checks user permissions to execute a command
 * Map values contain int set of user roles id.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see Command
 * @see User
 */
public class CommandPermissionValidator {
    private static final Map<Class<? extends Command>, Set<Integer>> permissions;

    static {
        permissions = new HashMap<>();
        //Admin commands
        permissions.put(UserManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(CompilationManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(GenreManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(ReviewManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(SingerManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(SongManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AlbumManagementPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminUpdateUserCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminDeleteReviewCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminUpdateSingerCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCreateSingerCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCreateGenreCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminUpdateGenreCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminUpdateAlbumCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCreateAlbumCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCompilationPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCreateCompilationCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminDeleteCompilationCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminCreateSongCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(AdminUpdateSongCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));
        //User commands
        permissions.put(BasketRemoveCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(BasketAddCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(CreateOrderCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(OrderRemoveSongCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(PayOrderCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(DownloadOrderCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
    }

    private CommandPermissionValidator() {
    }

    /**
     * IsUserHasPermission: method validates current user permissions to execute command
     * If user or command parameter is null- returns false
     * If user has status "blocked" - returns false
     * Permission map uses Command.class as key and set of int as values.
     * Method checks if map contains user role id and returns true or false
     *
     * @param user    - User.class object
     * @param command - current executed user command
     * @return the boolean
     */
    public static boolean isUserHasPermission(User user, Command command) {
        if (user != null && user.getLoginStatus() && command != null) {
            if (user.getBlockedStatus()) {
                return false;
            }
            Set<Integer> commandPermissions = permissions.get(command.getClass());
            return commandPermissions != null && commandPermissions.contains(user.getRoleId());
        } else {
            return false;
        }
    }
}
