package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.UserDaoImpl;
import by.verbitsky.servletdemo.model.service.UserService;
import by.verbitsky.servletdemo.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public enum UserServiceImpl implements UserService<User> {
    INSTANCE;

    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public Optional<User> findUserByEmail(String email) throws PoolException, ServiceException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginTransaction(userDao);
            result = userDao.findUserByEmail(email.toLowerCase());
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result;
    }

    public Optional<User> findUserByName(String userName) throws PoolException, ServiceException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginTransaction(userDao);
            result = userDao.findUserByName(userName.toLowerCase());
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result;
    }

    public Optional<String> findUserPassword(String userName) throws PoolException, ServiceException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        Optional<String> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginTransaction(userDao);
            result = userDao.findUserPassword(userName);
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result;
    }

    public void addRegisteredUser(User user, String password) throws PoolException, ServiceException {
        ProxyConnection connection = ConnectionPoolImpl.getInstance().getConnection();
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.beginTransaction(userDao);
            userDao.create(user);
            userDao.updateUserPassword(user, password);
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
    }
}