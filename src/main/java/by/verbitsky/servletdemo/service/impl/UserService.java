package by.verbitsky.servletdemo.service.impl;

import by.verbitsky.servletdemo.dao.UserDao;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;

public enum UserService {
    INSTANCE;
    private final ConnectionPoolImpl pool = ConnectionPoolImpl.getInstance();

    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public User findUserByEmail(String email) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findByEmail(email.toLowerCase());
        pool.releaseConnection(connection);
        return user;
    }

    public User findUserByName(String userName) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        User user = userDAO.findUserByName(userName.toLowerCase());
        pool.releaseConnection(connection);
        return user;
    }

    public boolean addRegisteredUser(User user) throws PoolException, DaoException {
        UserDao userDAO = new UserDao();
        ProxyConnection connection = pool.getConnection();
        userDAO.setConnection(connection);
        boolean result = userDAO.create(user);
        pool.releaseConnection(connection);
        return result;
    }
}
