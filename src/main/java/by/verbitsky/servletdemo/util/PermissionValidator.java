package by.verbitsky.servletdemo.util;

import by.verbitsky.servletdemo.command.Command;
import by.verbitsky.servletdemo.command.impl.AdminPageCommand;
import by.verbitsky.servletdemo.entity.User;

import java.util.HashMap;
import java.util.Map;

public class PermissionValidator {
    private static final Map<Command, Integer> permissions;

    static {
        permissions = new HashMap<>();
        //todo дописать страницы с доступом по роли
        permissions.put(new AdminPageCommand(), 1);
    }

    private PermissionValidator() {
    }

    public static boolean isUserHasPermission(User user, Command command) {
        boolean result;
        if (user != null && user.getLoginStatus() && command != null) {
            Integer permissionId = permissions.get(command);
            result = permissionId != null && user.getRoleId() == permissionId;
        } else {
            result = false;
        }
        return result;
    }
}
