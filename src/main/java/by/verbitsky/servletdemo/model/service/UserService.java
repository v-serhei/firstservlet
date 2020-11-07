package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    boolean addRegisteredUser(User user, String password) throws PoolException, ServiceException;

    Optional<String> findUserPassword(String userName) throws PoolException, ServiceException;

    Optional<User> findUserByName(String userName) throws PoolException, ServiceException;

    boolean isExistEmail(String email) throws PoolException, ServiceException;
}
