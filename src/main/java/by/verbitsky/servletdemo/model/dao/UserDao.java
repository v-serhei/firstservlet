package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.DaoException;

import java.util.Optional;

/**
 * The interface User dao extends {@link BaseDao} to provide C.R.U.D. methods for User objects in application data base
 * UserDao interface also provides additional methods to process user commands
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.controller.command.Command
 * @see User
 */
public interface UserDao extends BaseDao<User> {

    Optional<User> findUserByEmail(String userEmail) throws DaoException;

    Optional<User> findUserByName(String userName) throws DaoException;

    Optional<String> findUserPassword(String userName) throws DaoException;

    boolean updateUserPassword(User user, String password) throws DaoException;
}