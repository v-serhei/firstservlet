package by.verbitsky.servletdemo.model.service.impl;

enum UserRole {
    ADMIN (99),
    REGISTERED_USER(1);

    UserRole (int roleId) {
        this.roleId = roleId;
    }
    int roleId;

    public int getRoleId() {
        return roleId;
    }
}
