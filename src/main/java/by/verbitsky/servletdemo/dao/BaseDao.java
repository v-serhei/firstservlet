package by.verbitsky.servletdemo.dao;

import by.verbitsky.servletdemo.exception.DaoException;

import java.util.List;

public abstract class BaseDao<E, T> {
    public abstract List<E> findAll() throws DaoException;
    public abstract E findById(T id) throws DaoException;
    public abstract E update(E entity);
    public abstract boolean delete(T id) throws DaoException;
    public abstract boolean create(E entity) throws DaoException;
}
