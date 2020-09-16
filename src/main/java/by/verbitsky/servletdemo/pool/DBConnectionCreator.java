package by.verbitsky.servletdemo.pool;

import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;

public interface DBConnectionCreator<T extends ProxyConnection> {
    ProxyConnection createConnection() throws PoolException;

    void deregisterDBDriver() throws PoolException;
}
