package by.verbitsky.servletdemo.model.service.impl;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.controller.command.AttributeName;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;
import by.verbitsky.servletdemo.model.dao.Transaction;
import by.verbitsky.servletdemo.model.dao.UserDao;
import by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;
import by.verbitsky.servletdemo.model.service.DaoFactory;
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
    private DaoFactory daoFactory = new DaoFactoryImpl();

    @Override
    public int getAdminRoleId() {
        return UserRole.ADMIN.getRoleId();
    }

    @Override
    public int getRoleIdByDescription(String roleName) throws ServiceException {
        try {
            return UserRole.valueOf(roleName.toUpperCase()).roleId;
        } catch (IllegalArgumentException exception) {
            throw new ServiceException("UserServiceImpl: received unknown role name", exception);
        }
    }

    @Override
    public int getBlockedStatusIdByDescription(String status) {
        return status.equalsIgnoreCase(STATUS_ACTIVE) ? STATUS_ACTIVE_ID : STATUS_BLOCKED_ID;
    }

    @Override
    public List<String> getRoleList() {
        return Arrays.stream(UserRole.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @Override
    public String getRoleNameById(int id) {
        Optional<UserRole> userRole = Arrays.stream(UserRole.values()).filter(item -> item.getRoleId() == id).findFirst();
        if (userRole.isPresent()) {
            return userRole.get().toString();
        }
        return EMPTY_ROLE_DESCRIPTION;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        ProxyConnection connection = askConnectionFromPool();
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findAll users: received Dao exception", e);
        }
    }

    @Override
    public Optional<User> findUserByName(String userName) throws ServiceException {
        if (userName == null) {
            throw new ServiceException("UserServiceImpl find user by name: received null parameter userName");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByName(userName.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserByName: received Dao exception", e);
        }
        return result;
    }

    @Override
    public boolean checkUserLogin(String userName, String password, SessionRequestContent content) throws ServiceException {
        boolean loginResult = false;
        Optional<User> dbUserByName = findUserByName(userName);
        Optional<String> dbUserPassword = findUserPassword(userName);
        if (dbUserByName.isPresent() && dbUserPassword.isPresent()) {
            if (dbUserPassword.get().equals(getHashedPassword(password))) {
                dbUserByName.get().setLoginStatus(true);
                dbUserByName.get().initBasket();
                dbUserByName.get().setAdminRoleFlag(dbUserByName.get().getRoleId() == getAdminRoleId());
                content.addSessionAttribute(AttributeName.SESSION_USER, dbUserByName.get());
                return true;
            }
        }
        return loginResult;
    }

    @Override
    public boolean checkBlockedStatus(String userName) throws ServiceException {
        boolean result = false;
        Optional<User> dbUserByName = findUserByName(userName);
        if (dbUserByName.isPresent()) {
            if (dbUserByName.get().getBlockedStatus()) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean addRegisteredUser(User user, String password) throws ServiceException {
        if (user == null || password == null) {
            throw new ServiceException("UserServiceImpl add user: received null parameters");
        }
        if (password.isEmpty()) {
            throw new ServiceException("UserServiceImpl add user: received empty password");
        }
        ProxyConnection connection = askConnectionFromPool();
        boolean result;
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(userDao);
            String hashedPassword = getHashedPassword(password);
            result = (userDao.create(user) && userDao.updateUserPassword(user, hashedPassword));
            transaction.commitTransaction();
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl addRegisteredUser: received Dao exception", e);
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("UserServiceImpl update user email: received null user");
        }
        ProxyConnection connection = askConnectionFromPool();
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(userDao);
            boolean updateResult = userDao.update(user);
            transaction.commitTransaction();
            return updateResult;
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl update user email: error while updating user", e);
        }
    }

    @Override
    public boolean updateUserPassword(User user, String passwordOne, String passwordTwo, SessionRequestContent
            content) throws ServiceException {
        if (user == null || passwordOne == null || passwordTwo == null || content == null) {
            throw new ServiceException("UserServiceImpl update user password: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        if (!FieldDataValidator.isValidUserPassword(passwordOne)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_PASSWORD, true);
            return false;
        }
        if (!passwordOne.equals(passwordTwo)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_DIFFERENT_PASSWORDS, true);
            return false;
        }
        String password = getHashedPassword(passwordOne);
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processTransaction(userDao);
            boolean result = (userDao.updateUserPassword(user, password));
            transaction.commitTransaction();
            return result;
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl updateUserPassword: received Dao exception", e);
        }
    }

    @Override
    public boolean updateUserEmail(User user, String email, SessionRequestContent content) throws ServiceException {
        if (user == null || email == null || content == null) {
            throw new ServiceException("UserServiceImpl update user email: received null parameters");
        }
        ProxyConnection connection = askConnectionFromPool();
        if (!FieldDataValidator.isValidUserEmail(email)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_EMAIL, true);
            return false;
        }
        if (findUserByEmail(email)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, true);
            return false;
        } else {
            try (Transaction transaction = new Transaction(connection)) {
                UserDao userDao = daoFactory.getUserDao();
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
    }

    @Override
    public boolean validateRegistrationInputs(String userName, String firstPassword, String secondPassword, String
            userEmail, SessionRequestContent content) throws ServiceException {
        if (userName == null || firstPassword == null || secondPassword == null || userEmail == null || content == null) {
            throw new ServiceException("UserServiceImpl validateUserInputs: received null parameters");
        }
        boolean result = true;
        if (!FieldDataValidator.isValidUserName(userName)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_NAME, true);
            result = false;
        }
        if (!FieldDataValidator.isValidUserPassword(firstPassword)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_PASSWORD, true);
            result = false;
        }
        if (!firstPassword.equals(secondPassword)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_DIFFERENT_PASSWORDS, true);
            result = false;
        }
        if (!FieldDataValidator.isValidUserEmail(userEmail)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_WRONG_EMAIL, true);
            result = false;
        }
        Optional<User> user = findUserByName(userName);
        if (user.isPresent()) {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_NAME, true);
            result = false;
        }
        if (findUserByEmail(userEmail)) {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, true);
            result = false;
        } else {
            content.addRequestAttribute(AttributeName.REGISTRATION_EXIST_EMAIL, false);
        }
        return result;
    }

    @Override
    public boolean validateLoginInputs(String userName, String firstPassword, SessionRequestContent content) {
        if (userName == null || firstPassword == null || content == null) {
            return false;
        }
        boolean result = true;
        if (!FieldDataValidator.isValidUserName(userName)) {
            content.addRequestAttribute(AttributeName.LOGIN_FAILED, true);
            result = false;
        }
        if (!FieldDataValidator.isValidUserPassword(firstPassword)) {
            content.addRequestAttribute(AttributeName.LOGIN_FAILED, true);
            result = false;
        }
        return result;
    }

    private String getHashedPassword(String password) {
        String salt = "Something very salt";
        return DigestUtils.sha512Hex(salt.concat(password));
    }

    private boolean findUserByEmail(String email) throws ServiceException {
        if (email == null) {
            throw new ServiceException("UserServiceImpl find user by email: received null parameter email");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<User> result;
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserByEmail(email.toLowerCase());
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserByEmail: received Dao exception", e);
        }
        return result.isPresent();
    }

    private Optional<String> findUserPassword(String userName) throws ServiceException {
        if (userName == null) {
            throw new ServiceException("UserServiceImpl find user password: received null parameter userName");
        }
        ProxyConnection connection = askConnectionFromPool();
        Optional<String> result;
        UserDao userDao = daoFactory.getUserDao();
        try (Transaction transaction = new Transaction(connection)) {
            transaction.processSimpleQuery(userDao);
            result = userDao.findUserPassword(userName);
        } catch (DaoException e) {
            throw new ServiceException("UserServiceImpl findUserPassword: received Dao exception", e);
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