package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.UserDaoImpl;
import by.verbitsky.servletdemo.model.service.UserService;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;

public enum UserServiceImpl implements UserService {
    INSTANCE;

    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public boolean isExistEmail(String email) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByEmail(email.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result.isPresent();
    }

    public Optional<User> findUserByName(String userName) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByName(userName.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByName\"", e);
        }
        return result;
    }

    public Optional<String> findUserPassword(String userName) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        Optional<String> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserPassword(userName);

        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result;
    }

    public void addRegisteredUser(User user, String password) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processTransaction(userDao);
            userDao.create(user);
            userDao.updateUserPassword(user, password);
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
    }

    private ProxyConnection askConnectionFromPool () throws ServiceException {
        ProxyConnection result;
        try {
            result = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("UserServiceImpl: error while receiving connection from pool");
        }
        return result;
    }
}