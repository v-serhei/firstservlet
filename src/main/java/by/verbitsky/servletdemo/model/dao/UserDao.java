package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao <User> {
    Optional <User> findUserByEmail(String userEmail) throws DaoException;

    Optional <User> findUserByName(String userName) throws DaoException;

    Optional <String> findUserPassword (String userName) throws DaoException;

    boolean updateUserPassword (User user, String password) throws DaoException;
}