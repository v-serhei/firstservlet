package by.verbitsky.servletdemo.controller.command;

import by.verbitsky.servletdemo.controller.command.impl.AdminPageCommand;
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
        permissions.put(AdminPageCommand.class, Stream.of(99).collect(Collectors.toCollection(HashSet::new)));

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
        boolean result;
        if (user != null && user.getLoginStatus() && command != null) {
            Set<Integer> commandPermissions = permissions.get(command.getClass());
            result = commandPermissions != null && commandPermissions.contains(user.getRoleId());
        } else {
            result = false;
        }
        return result;
    }
}
