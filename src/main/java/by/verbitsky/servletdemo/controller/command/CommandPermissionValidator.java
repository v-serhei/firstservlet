package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.admin.*;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.AdminCompilationPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.AlbumManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.CompilationManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.GenreManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.SongManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.ReviewManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.SingerManagementPageCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.ready.UserManagementPageCommand;
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

        //User commands
        permissions.put(BasketRemoveCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(BasketAddCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(CreateOrderCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(OrderRemoveSongCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        permissions.put(PayOrderCommand.class, Stream.of(1).collect(Collectors.toCollection(HashSet::new)));
        //common commands
    }

    private CommandPermissionValidator() {
    }

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
