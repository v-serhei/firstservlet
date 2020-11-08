package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.impl.UserDaoImpl;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.UserService;
import by.verbitsky.servletdemo.util.FieldDataValidator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Optional;

public enum UserServiceImpl implements UserService {
    INSTANCE;
    private static final String EMPTY_ROLE_DESCRIPTION = "";

    public int getAdminRoleId() {
        return UserRole.ADMIN.getRoleId();
    }

    public String getRoleNameById(int id) {
        Optional<UserRole> userRole = Arrays.stream(UserRole.values()).filter(item -> item.getRoleId() == id).findFirst();
        if (userRole.isPresent()) {
            return  userRole.get().toString();
        }
        return EMPTY_ROLE_DESCRIPTION;
    }


    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public boolean findUserByEmail(String email) throws ServiceException {
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

    public boolean addRegisteredUser(User user, String password) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processTransaction(userDao);
            result = (userDao.create(user) && userDao.updateUserPassword(user, password));
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserService: received Dao exception while processing \"findUserByEmail\"", e);
        }
        return result;
    }


    public boolean updateUser(User user) {

        return true;
    }

    public boolean updateUserPassword(User user, String passwordOne, String passwordTwo, SessionRequestContent content) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        if (FieldDataValidator.isValidUserPasswords(passwordOne, passwordTwo, content)) {
            String password = getHashedPassword(passwordOne);
            try (Transaction transaction = new Transaction(connection)) {
                UserDaoImpl userDao = new UserDaoImpl();
                transaction.processSimpleQuery(userDao);
                return (userDao.updateUserPassword(user, password));
            } catch (DaoException e) {
                throw new ServiceException("UserService: received Dao exception updating password", e);
            }
        } else {
            return false;
        }
    }

    public boolean updateUserEmail(User user, String email, SessionRequestContent content) throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        if (FieldDataValidator.isValidUserEmail(email, content)) {
            if (findUserByEmail(email)) {
                content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, true);
                return false;
            } else {
                try (Transaction transaction = new Transaction(connection)) {
                    UserDaoImpl userDao = new UserDaoImpl();
                    transaction.processSimpleQuery(userDao);
                    String oldEmail = user.getEmail();
                    user.setEmail(email);
                    boolean result = (userDao.update(user));
                    if (result) {
                        return true;
                    } else {
                        user.setEmail(oldEmail);
                        return false;
                    }
                } catch (DaoException e) {
                    throw new ServiceException("UserService: received Dao exception updating password", e);
                }
            }
        } else {
            return false;
        }
    }

    public boolean validateUserInputs(String userName, String firstPassword,
                                      String secondPassword, String userEmail,
                                      SessionRequestContent content) throws ServiceException {
        boolean result = false;
        if (!FieldDataValidator.isValidUserName(userName, content)) {
            result = true;
        }
        if (!FieldDataValidator.isValidUserPasswords(firstPassword, secondPassword, content)) {
            result = true;
        }
        if (!FieldDataValidator.isValidUserEmail(userEmail, content)) {
            result = true;
        }
        Optional<User> user = findUserByName(userName);
        if (user.isPresent()) {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_NAME, true);
            result = true;
        }
        if (findUserByEmail(userEmail)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, true);
            result = true;
        } else {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, false);
        }
        return result;
    }

    private ProxyConnection askConnectionFromPool() throws ServiceException {
        ProxyConnection result;
        try {
            result = ConnectionPoolImpl.getInstance().getConnection();
        } catch (PoolException e) {
            throw new ServiceException("UserServiceImpl: error while receiving connection from pool");
        }
        return result;
    }
}