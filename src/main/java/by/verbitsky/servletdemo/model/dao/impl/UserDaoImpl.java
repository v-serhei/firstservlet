package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.entity.UserBuilder;
import by.verbitsky.servletdemo.entity.impl.UserBuilderImpl;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String SELECT_ALL_USERS =
            "SELECT username, email, role_id, blocked_status, discount_value FROM users";
    private static final String SELECT_USER_BY_ID =
            "SELECT username, email, role_id, blocked_status, discount_value FROM users WHERE user_id=?";
    private static final String SELECT_USER_BY_EMAIL =
            "SELECT username, email, role_id, blocked_status, discount_value FROM users WHERE email=?";
    private static final String SELECT_USER_BY_NAME =
            "SELECT username, password, email, role_id, blocked_status, discount_value FROM users WHERE username=?";
    private static final String SELECT_DELETE_USER_BY_ID =
            "DELETE FROM users WHERE user_id=?";
    private static final String INSERT_USER =
            "INSERT INTO users (`username`, `email`) VALUES (?, ?)";
    private static final String UPDATE_USER_PASSWORD =
            "UPDATE users SET password = ? WHERE username = ?";

    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ROLE = "role_id";
    private static final String COLUMN_BLOCKED = "blocked_status";
    private static final String COLUMN_DISCOUNT = "discount_value";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                User user = buildUser(set);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<User> findEntity(long offset, int limit) throws DaoException {
        throw new DaoException("Unsupported method call");
    }


    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        if (id != null) {
            User user = null;
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
                statement.setLong(1, id);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    user = buildUser(set);
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            Optional<User> result;
            if (user != null) {
                result = Optional.of(user);
            } else {
                result = Optional.empty();
            }
            return result;
        } else {
            throw new DaoException("Find user by id: received null id");
        }
    }

    @Override
    public Optional<User> findUserByEmail(String userEmail) throws DaoException {
        if (userEmail != null) {
            return findUser(userEmail, SELECT_USER_BY_EMAIL);
        } else {
            throw new DaoException("Find user by email: received null email");
        }
    }

    @Override
    public Optional<User> findUserByName(String userName) throws DaoException {
        if (userName != null) {
            return findUser(userName, SELECT_USER_BY_NAME);
        } else {
            throw new DaoException("Find user by name: received null or empty userName");
        }
    }

    @Override
    public Optional<String> findUserPassword(String userName) throws DaoException {
        String password;
        Optional<String> result;
        if (userName != null || !userName.isEmpty()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
                statement.setString(1, userName);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    password = set.getString(COLUMN_PASSWORD);
                    result = Optional.of(password);
                } else {
                    result = Optional.empty();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {
            throw new DaoException("Find user password: received null or empty userName");
        }
        return result;
    }

    @Override
    public void updateUserPassword(User user, String password) throws DaoException {
        if (user != null || password != null || !password.isEmpty()) {
            String userName = user.getUserName();
            if (userName != null || !userName.isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
                    statement.setString(1, password);
                    statement.setString(2, userName);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new DaoException("Update user password: error while updating user password",e);
                }
            } else {
                throw new DaoException("Update user password: received null or empty user name");
            }
        } else {
            throw new DaoException("Update user password: received null or empty user or password value");
        }
    }

    @Override
    public boolean create(User user) throws DaoException {
        boolean result;
        if (user != null) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getEmail());
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

    @Override
    public long calculateRowCount() throws DaoException {
        return 0;
    }

    @Override
    public boolean update(long id, User entity) {
        //todo release this (admin)
        return true;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        if (id != null) {
            boolean result;
            try (PreparedStatement statement = connection.prepareStatement(SELECT_DELETE_USER_BY_ID)) {
                statement.setLong(1, id);
                statement.executeQuery();
                result = true;
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            return result;
        } else {
            throw new DaoException("Delete user by id: received null id");
        }
    }

    private Optional<User> findUser(String userName, String query) throws DaoException {
        Optional<User> result;
        User user;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                user = buildUser(set);
                result = Optional.of(user);
            } else {
                result = Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private User buildUser(ResultSet dbResultSet) throws SQLException {
        UserBuilder builder = new UserBuilderImpl();
        builder.setUserName(dbResultSet.getString(COLUMN_USERNAME));
        builder.setEmail(dbResultSet.getString(COLUMN_EMAIL));
        builder.setRoleId(Integer.parseInt(dbResultSet.getString(COLUMN_ROLE)));
        builder.setBlockedStatus(Integer.parseInt(dbResultSet.getString(COLUMN_BLOCKED)));
        builder.setDiscount(Integer.parseInt(dbResultSet.getString(COLUMN_DISCOUNT)));
        return builder.buildUser();
    }
}