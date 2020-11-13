package by.verbitsky.servletdemo.model.dao.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.model.dao.UserFactory;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.dao.AbstractDao;
import by.verbitsky.servletdemo.model.dao.UserDao;
import by.verbitsky.servletdemo.util.SqlRegexGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String SELECT_ALL_USERS =
            "Select user_id, username, email, role_id, blocked_status, discount_value, reg_date from users order by username";
    private static final String SELECT_USER_BY_ID =
            "Select user_id, username, email, role_id, blocked_status, discount_value, reg_date from users where user_id=?";
    private static final String SELECT_USER_BY_EMAIL =
            "Select user_id, username, email, role_id, blocked_status, discount_value, reg_date FROM users where email=?";
    private static final String SELECT_USER_BY_NAME =
            "Select user_id, username, password, email, role_id, blocked_status, discount_value, reg_date from users where username=?";
    private static final String DELETE_USER_BY_ID =
            "Delete from users where user_id=?";
    private static final String INSERT_USER =
            "Insert Into users (`username`, `email`) values (?, ?)";
    private static final String UPDATE_USER_PASSWORD =
            "Update users Set password = ? where username = ?";

    private static final String UPDATE_USER =
            "Update users " +
            "Set email          = ?," +
            "    role_id        = ?," +
            "    blocked_status = ?," +
            "    discount_value = ? " +
            "where users.username = ?;";

    private static final String COLUMN_PASSWORD = "password";

    private UserFactory<User> factory = new UserFactoryImpl();
    @Override
    public List<User> findAll() throws DaoException {
        List<User> result;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet set = statement.executeQuery();
            result = factory.createUserList(set);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        if (id != null) {
            Optional<User> result = Optional.empty();
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
                statement.setLong(1, id);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    result = factory.createUser(set);
                }
            } catch (SQLException e) {
                throw new DaoException(e);
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
        Optional<String> result = Optional.empty();
        if (userName != null || !userName.isEmpty()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
                statement.setString(1, userName);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    password = set.getString(COLUMN_PASSWORD);
                    result = Optional.of(password);
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
    public boolean updateUserPassword(User user, String password) throws DaoException {
        if (user != null || password != null || !password.isEmpty()) {
            String userName = user.getUserName();
            if (userName != null || !userName.isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {
                    statement.setString(1, password);
                    statement.setString(2, userName);
                    statement.executeUpdate();
                    return true;
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
        boolean result = false;
        if (user != null) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getEmail());
                int count = statement.executeUpdate();
                if (count > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getLong(1));
                        result = true;
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return result;
    }

    @Override
    public boolean update(User user) throws DaoException {
        if (user != null) {
            String userName = user.getUserName();
            if (userName != null || !userName.isEmpty()) {
                try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
                    statement.setString(1, SqlRegexGenerator.escapeEmailParameter(user.getEmail()));
                    statement.setInt(2, user.getRoleId());
                    if (user.getBlockedStatus()) {
                        statement.setInt(3, 1);
                    }else {
                        statement.setInt(3, 0);
                    }
                    statement.setInt(4, user.getDiscount());
                    statement.setString(5, user.getUserName());
                    int count = statement.executeUpdate();
                    return count > 0;
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
    public boolean delete(Long id) throws DaoException {
        if (id != null) {
            boolean result;
            try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)) {
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
        Optional<User> result = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = factory.createUser(set);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }
}