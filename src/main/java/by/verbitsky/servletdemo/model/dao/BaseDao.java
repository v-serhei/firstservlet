package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao provides C.R.U.D. methods for objects in application data base
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.controller.command.Command
 */
public interface BaseDao<E> {

    /**
     * Sets connection as field of Dao impl that used to process operations with data base
     *
     * @param connection - contains connection with data base
     * @throws DaoException the dao exception if received null
     */
    void setConnection(Connection connection) throws DaoException;

    List<E> findAll() throws DaoException;

    Optional<E> findEntityById(Long id) throws DaoException;

    boolean update(E entity) throws DaoException;

    boolean delete(Long id) throws DaoException;

    boolean create(E entity) throws DaoException;
}