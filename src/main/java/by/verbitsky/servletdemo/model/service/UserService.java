package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.Optional;

public interface UserService <User> {
    void addRegisteredUser(User user, String password) throws PoolException, ServiceException;

    Optional<String> findUserPassword(String userName) throws PoolException, ServiceException;

    Optional<User> findUserByName(String userName) throws PoolException, ServiceException;

    Optional<User> findUserByEmail(String email) throws PoolException, ServiceException;
}
