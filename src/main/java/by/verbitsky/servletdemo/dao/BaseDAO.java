package by.verbitsky.servletdemo.dao;

import java.util.List;

public abstract class BaseDAO <E, T> {
    public abstract List<E> findAll();
    public abstract E findEntityById(T id);
    public abstract E update(E entity);
    public abstract boolean delete(T id);
    public abstract boolean create(E entity);
}
