package by.verbitsky.servletdemo.model.pool;

import by.verbitsky.servletdemo.exception.PoolException;

import java.sql.Connection;

/**
 * The interface Connection pool define common methods for pool of data base connections.
 * To start pool it should be initialized by init method call. Method init prepares pool and
 * let it to load all property to connect with data base.
 * <p>
 *
 * @param <T> the type parameter
 *            <p>
 * @author Verbitsky Sergey
 * @version 1.0
 * @see by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl
 */
public interface ConnectionPool<T extends Connection> {

    /**
     * Init connection pool. Load connection properties or init Property manager to load it from
     * property files
     */
    void initConnectionPool();

    /**
     * Gets connection. Provide free connection from pool queue
     *
     * @return the connection
     * @throws PoolException the pool exception
     */
    T getConnection() throws PoolException;

    /**
     * Release connection. Takes back connection to pool queue
     *
     * @param connection the connection
     * @throws PoolException the pool exception
     */
    void releaseConnection(T connection) throws PoolException;

    /**
     * Shutdown pool. Close all pool connections. It should wait while all connection will be returned back to pool.
     *
     * @throws PoolException the pool exception
     */
    void shutdownPool() throws PoolException;
}
