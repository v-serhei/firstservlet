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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum UserServiceImpl implements UserService {
    INSTANCE;
    private static final String EMPTY_ROLE_DESCRIPTION = "";
    private static final String STATUS_ACTIVE = "active";
    private static final int STATUS_ACTIVE_ID = 0;
    private static final int STATUS_BLOCKED_ID = 1;

    public int getAdminRoleId() {
        return UserRole.ADMIN.getRoleId();
    }

    public int getRoleIdByDescription(String roleName) throws ServiceException {
        try {
            return UserRole.valueOf(roleName.toUpperCase()).roleId;
        } catch (IllegalArgumentException exception) {
            throw new ServiceException("UserServiceImpl: received unknown role name", exception);
        }
    }

    public int getBlockedStatusIdByDescription(String status) {
        return status.equalsIgnoreCase(STATUS_ACTIVE) ? STATUS_ACTIVE_ID : STATUS_BLOCKED_ID;
    }

    public List<String> getRoleList() {
        return Arrays.stream(UserRole.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public String getRoleNameById(int id) {
        Optional<UserRole> userRole = Arrays.stream(UserRole.values()).filter(item -> item.getRoleId() == id).findFirst();
        if (userRole.isPresent()) {
            return userRole.get().toString();
        }
        return EMPTY_ROLE_DESCRIPTION;
    }

    public String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    public boolean findUserByEmail(String email) throws ServiceException {
        if (email == null) {
            throw new ServiceException("UserServiceImpl find user by email: received null parameter email");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByEmail(email.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserByEmail: received Dao exception", e);
        }
        return result.isPresent();
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findAll users: received Dao exception", e);
        }
    }

    public Optional<User> findUserByName(String userName) throws ServiceException {
        if (userName == null) {
            throw new ServiceException("UserServiceImpl find user by name: received null parameter userName");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByName(userName.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserByName: received Dao exception", e);
        }
        return result;
    }

    public Optional<String> findUserPassword(String userName) throws ServiceException {
        if (userName == null) {
            throw new ServiceException("UserServiceImpl find user password: received null parameter userName");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<String> result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserPassword(userName);
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserPassword: received Dao exception", e);
        }
        return result;
    }

    public boolean addRegisteredUser(User user, String password) throws ServiceException {
        if (user == null || password == null) {
            throw new ServiceException("UserServiceImpl add user: received null parameters");
        }
        if (password.isEmpty()) {
            throw new ServiceException("UserServiceImpl add user: received empty password");
        }
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        try (Transaction transaction = new Transaction(connection)) {
            UserDaoImpl userDao = new UserDaoImpl();
            transaction.processTransaction(userDao);
            result = (userDao.create(user) && userDao.updateUserPassword(user, password));
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl addRegisteredUser: received Dao exception", e);
        }
        return result;
    }


    public boolean updateUser(User user) throws ServiceException {
        if(user == null) {
            throw new ServiceException("UserServiceImpl update user email: received null user");
        }
        ProxyConnection connection = askConnectionFromPool();
        try (Transaction transaction = new Transaction(connection)){
            UserDaoImpl dao = new UserDaoImpl();
            transaction.processTransaction(dao);
            boolean updateResult = dao.update(user);
            transaction.commitTransaction();
            return updateResult;
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl update user email: error while updating user",e);
        }
    }

    public boolean updateUserPassword(User user, String passwordOne, String passwordTwo, SessionRequestContent content) throws ServiceException {
        if (user == null || passwordOne == null || passwordTwo == null || content == null) {
            throw new ServiceException("UserServiceImpl update user password: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        if (FieldDataValidator.isValidUserPasswords(passwordOne, passwordTwo, content)) {
            String password = getHashedPassword(passwordOne);
            try (Transaction transaction = new Transaction(connection)) {
                UserDaoImpl userDao = new UserDaoImpl();
                transaction.processTransaction(userDao);
                boolean result = (userDao.updateUserPassword(user, password));
                transaction.commitTransaction();
                return result;
            } catch (DaoException e) {
                throw new ServiceException("UserServiceImpl updateUserPassword: received Dao exception", e);
            }
        } else {
            return false;
        }
    }

    public boolean updateUserEmail(User user, String email, SessionRequestContent content) throws ServiceException {
        if (user == null || email == null || content == null) {
            throw new ServiceException("UserServiceImpl update user email: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        if (FieldDataValidator.isValidUserEmail(email, content)) {
            if (findUserByEmail(email)) {
                content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, true);
                return false;
            } else {
                try (Transaction transaction = new Transaction(connection)) {
                    UserDaoImpl userDao = new UserDaoImpl();
                    transaction.processTransaction(userDao);
                    String oldEmail = user.getEmail();
                    user.setEmail(email);
                    boolean result = (userDao.update(user));
                    transaction.commitTransaction();
                    if (result) {
                        return true;
                    } else {
                        user.setEmail(oldEmail);
                        return false;
                    }
                } catch (DaoException e) {
                    throw new ServiceException("UserServiceImpl updateUserEmail: received Dao exception", e);
                }
            }
        } else {
            return false;
        }
    }

    public boolean validateUserInputs(String userName, String firstPassword, String secondPassword, String userEmail,
                                      SessionRequestContent content) throws ServiceException {
        if (userName == null || firstPassword == null || secondPassword == null || userEmail == null || content == null) {
            throw new ServiceException("UserServiceImpl validateUserInputs: received null parameters");
        }
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