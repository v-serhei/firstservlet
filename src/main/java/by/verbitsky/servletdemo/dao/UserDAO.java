package by.verbitsky.servletdemo.dao;

import by.verbitsky.servletdemo.entity.WebUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO <WebUser, Long>{
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email='";
    private static final String GET_USER_BY_NAME = "SELECT * FROM users WHERE username='";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=";
    private static final String INSERT_USER = "INSERT INTO users (username, password, email) VALUES ('";
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String EMAIL_COLUMN = "email";
    private static final String END_OF_INSERT_QUERY = "')";
    private static final String END_OF_SELECT_WHERE_QUERY = "'";
    private static final String VALUE_DELIMITER = "', '";
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<WebUser> getAll() {
        List<WebUser> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String name = set.getString(USERNAME_COLUMN);
                String email = set.getString(EMAIL_COLUMN);
                String password = set.getString(PASSWORD_COLUMN);
                WebUser user = new WebUser(name, password, email);
                result.add(user);
            }
        } catch (SQLException throwables) {
            //todo log this
        }
        //todo log this
        return result;
    }

    @Override
    public WebUser getEntityById(Long id) {
        long userId = (Long) id;
        WebUser user = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID.concat(String.valueOf(userId)))) {
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String name = set.getString(USERNAME_COLUMN);
                String email = set.getString(EMAIL_COLUMN);
                String password = set.getString(PASSWORD_COLUMN);
                user = new WebUser(name, password, email);
            }
        } catch (SQLException throwables) {
            //todo log this
        }
        //todo log this
        return user;
    }

    public boolean existUserEmail(String email) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(GET_USER_BY_EMAIL);
            sb.append(email.toLowerCase());
            sb.append(END_OF_SELECT_WHERE_QUERY);
            statement = connection.prepareStatement(sb.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = true;
            }
        } catch (SQLException throwables) {
            //todo log this
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //todo log this
                }
            }
        }
        return result;
    }

    public boolean existUserName(String name) {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(GET_USER_BY_NAME);
            sb.append(name.toLowerCase());
            sb.append(END_OF_SELECT_WHERE_QUERY);
            statement = connection.prepareStatement(sb.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                result = true;
            }
        } catch (SQLException throwables) {
            //todo log this
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //todo log this
                }
            }
        }
        return result;
    }

    public WebUser getUserByName(String userName) {
        PreparedStatement statement = null;
        WebUser user = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(GET_USER_BY_NAME);
            sb.append(userName.toLowerCase());
            sb.append(END_OF_SELECT_WHERE_QUERY);
            statement = connection.prepareStatement(sb.toString());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String email = set.getString(EMAIL_COLUMN);
                String password = set.getString(PASSWORD_COLUMN);
                user = new WebUser(userName, password, email);
            }
        } catch (SQLException throwables) {
            //todo log this
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    //todo log this
                }
            }
        }
        return user;
    }

    @Override
    public WebUser update(WebUser entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        boolean result;
        long userId = (Long) id;
        WebUser user = null;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID.concat(String.valueOf(userId)))) {
            statement.executeQuery();
            result = true;
        } catch (SQLException throwables) {
            result = false;
            //todo log this
        }
        //todo log this
        return result;
    }

    @Override
    public boolean create(WebUser entity) {
        return false;
    }

    public boolean addNewUser(WebUser user, String password) {
        boolean result;
        if (user != null) {
            PreparedStatement statement = null;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(INSERT_USER);
                sb.append(user.getUserName().toLowerCase());
                sb.append(VALUE_DELIMITER);
                sb.append(password);
                sb.append(VALUE_DELIMITER);
                sb.append(user.getEmail().toLowerCase());
                sb.append(END_OF_INSERT_QUERY);
                statement = connection.prepareStatement(sb.toString());
                statement.executeUpdate();
                result = true;
            } catch (SQLException throwables) {
                result = false;
                //todo log this
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException throwables) {
                        //todo log this
                    }
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
