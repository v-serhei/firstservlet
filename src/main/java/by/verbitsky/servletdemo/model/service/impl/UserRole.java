package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.User;

/**
 * The enum User role contains user roles values
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see User
 */
enum UserRole {
    /**
     * Admin role.
     */
    ADMIN (99),
    /**
     * Registered user role.
     */
    REGISTERED_USER(1);

    UserRole (int roleId) {
        this.roleId = roleId;
    }

    /**
     * The Role id.
     */
    int roleId;

    public int getRoleId() {
        return roleId;
    }
}
