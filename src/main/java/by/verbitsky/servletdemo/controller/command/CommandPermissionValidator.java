package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.admin.UpdateUserCommand;
import by.verbitsky.servletdemo.controller.command.impl.adminnavigation.*;
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
        permissions.put(UpdateUserCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));

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
