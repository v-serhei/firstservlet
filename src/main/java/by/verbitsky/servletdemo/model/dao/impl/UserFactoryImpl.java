package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.model.dao.UserFactory;
import by.verbitsky.servletdemo.model.service.impl.UserServiceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFactoryImpl implements UserFactory<User> {
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role_id";
    private static final String COLUMN_BLOCKED = "blocked_status";
    private static final String COLUMN_DISCOUNT = "discount_value";
    private static final String COLUMN_DATE = "reg_date";

    @Override
    public Optional<User> createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        long userId = resultSet.getLong(COLUMN_ID);
        if (userId <= 0) {
            return Optional.empty();
        }
        user.setUserId(resultSet.getLong(COLUMN_ID));
        user.setUserName(resultSet.getString(COLUMN_USERNAME));
        user.setEmail(resultSet.getString(COLUMN_EMAIL));
        user.setDiscount(resultSet.getInt(COLUMN_DISCOUNT));
        user.setRoleId(resultSet.getInt(COLUMN_ROLE));
        user.setAdminRoleFlag(userId == UserServiceImpl.INSTANCE.getAdminRoleId());
        user.setBlockedStatus(resultSet.getInt(COLUMN_BLOCKED));
        user.setRegistrationDate(resultSet.getDate(COLUMN_DATE).toLocalDate());
        return Optional.of(user);
    }

    @Override
    public List<User> createUserList(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            Optional<User> user = createUser(resultSet);
            user.ifPresent(result::add);
        }
        return result;
    }
}
