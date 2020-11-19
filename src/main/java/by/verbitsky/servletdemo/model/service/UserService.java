package by.verbitsky.servletdemo.model.service;

import by.verbitsky.servletdemo.controller.SessionRequestContent;
import by.verbitsky.servletdemo.entity.User;
import by.verbitsky.servletdemo.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service defines common service methods for working with application users
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see User
 */

public interface UserService {
    boolean addRegisteredUser(User user, String password) throws ServiceException;

    boolean checkBlockedStatus(String userName) throws ServiceException;

    boolean checkUserLogin(String userName, String password, SessionRequestContent content) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;

    boolean updateUserEmail(User user, String email, SessionRequestContent content) throws ServiceException;

    boolean updateUserPassword(User user, String passwordOne, String passwordTwo, SessionRequestContent content) throws ServiceException;

    boolean validateLoginInputs(String userName, String firstPassword, SessionRequestContent content);

    boolean validateRegistrationInputs(String userName, String firstPassword, String secondPassword,
                                       String userEmail, SessionRequestContent content) throws ServiceException;

    int getAdminRoleId();

    int getBlockedStatusIdByDescription(String status);

    int getRoleIdByDescription(String roleName) throws ServiceException;

    List<String> getRoleList();

    String getRoleNameById(int id);

    List<User> findAllUsers() throws ServiceException;

    Optional<User> findUserByName(String userName) throws ServiceException;
}
