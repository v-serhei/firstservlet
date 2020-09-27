package by.verbitsky.servletdemo.dao;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<User, Long> {
    private static final String SELECT_ALL_USERS =
            "SELECT username, password, email, role_id, blocked, discount_value FROM users";
    private static final String SELECT_USER_BY_ID =
            "SELECT username, password, email, role_id, blocked, discount_value FROM users WHERE user_id=?";
    private static final String SELECT_USER_BY_EMAIL =
            "SELECT username, password, email, role_id, blocked, discount_value FROM users WHERE email=?";
    private static final String SELECT_USER_BY_NAME =
            "SELECT username, password, email, role_id, blocked, discount_value FROM users WHERE username=?";
    private static final String SELECT_DELETE_USER_BY_ID =
            "DELETE FROM users WHERE user_id=?";
    private static final String INSERT_USER =
            "INSERT INTO users " +
            "(username, password, email, role_id, blocked, discount_value) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role_id";
    private static final String COLUMN_BLOCKED = "blocked";
    private static final String COLUMN_DISCOUNT = "discount_value";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String name = set.getString(COLUMN_USERNAME);
                String email = set.getString(COLUMN_EMAIL);
                String password = set.getString(COLUMN_PASSWORD);
                int roleId = Integer.parseInt(set.getString(COLUMN_ROLE));
                int blocked = Integer.parseInt(set.getString(COLUMN_BLOCKED));
                int discount = Integer.parseInt(set.getString(COLUMN_DISCOUNT));
                User user = new User(name, password, email, roleId, blocked, discount);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public User findById(Long id) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = createUserFromSet(set);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public User findByEmail(String userEmail) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, userEmail);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = createUserFromSet(set);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public User findUserByName(String userName) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
            statement.setString(1, userName);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = createUserFromSet(set);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        boolean result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_DELETE_USER_BY_ID)) {
            statement.setLong(1, id);
            statement.executeQuery();
            result = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean create(User entity) throws DaoException {
        throw new DaoException("Method not supported");
    }

    public boolean addNewUser(User user) throws DaoException {
        boolean result;
        if (user != null) {
            //username, password, email, role_id, blocked, discount_value
            try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getUserPassword());
                statement.setString(3, user.getEmail());
                statement.setInt(4, user.getRoleId());
                statement.setInt(5, user.getBlockedStatus());
                statement.setInt(6, user.getDiscount());
                statement.executeUpdate();
                result = true;
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {
            result = false;
        }
        return result;
    }

    private User createUserFromSet(ResultSet set) throws SQLException {
        User user;
        String name = set.getString(COLUMN_USERNAME);
        String email = set.getString(COLUMN_EMAIL);
        String password = set.getString(COLUMN_PASSWORD);
        int roleId = Integer.parseInt(set.getString(COLUMN_ROLE));
        int blocked = Integer.parseInt(set.getString(COLUMN_BLOCKED));
        int discount = Integer.parseInt(set.getString(COLUMN_DISCOUNT));
        user = new User(name, password, email, roleId, blocked, discount);
        return user;
    }
}