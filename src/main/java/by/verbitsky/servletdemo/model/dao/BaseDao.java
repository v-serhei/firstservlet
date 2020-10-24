package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface BaseDao<E> {
    void setConnection (Connection connection) throws DaoException;

    List<E> findAll() throws DaoException;

    List<E> findEntity(long offset, int limit) throws DaoException;

    Optional <E> findEntityById(Long id) throws DaoException;

    boolean update(long id, E entity);

    boolean delete(Long id) throws DaoException;

    boolean create(E entity) throws DaoException;

    long calculateRowCount() throws DaoException;
}