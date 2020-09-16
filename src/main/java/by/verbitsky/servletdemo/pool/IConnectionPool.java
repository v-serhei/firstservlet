package by.verbitsky.servletdemo.pool;

import by.verbitsky.servletdemo.exception.PoolException;

import java.sql.Connection;

public interface IConnectionPool<T extends Connection> {
    T getConnection() throws PoolException;

    void releaseConnection(T connection) throws PoolException;
}
