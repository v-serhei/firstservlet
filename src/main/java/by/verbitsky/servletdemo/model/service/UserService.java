package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean addRegisteredUser(User user, String password) throws ServiceException;

    Optional<String> findUserPassword(String userName) throws ServiceException;

    Optional<User> findUserByName(String userName) throws  ServiceException;

    boolean findUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers () throws ServiceException;
}
