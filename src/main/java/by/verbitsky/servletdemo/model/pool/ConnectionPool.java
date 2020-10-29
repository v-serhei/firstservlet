package by.verbitsky.servletdemo.model.pool;

import by.verbitsky.servletdemo.exception.PoolException;

import java.sql.Connection;

public interface ConnectionPool<T extends Connection> {
    T getConnection() throws PoolException;

    void releaseConnection(T connection) throws PoolException;
}
