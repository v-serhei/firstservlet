package by.verbitsky.servletdemo.model.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolImpl implements ConnectionPool<ProxyConnection> {
    private static ConnectionPoolImpl instance;
    private static final String PROPERTY_POOL_SIZE = "poolSize";
    private boolean isInitialized;
    private static Lock WORK_LOCK = new ReentrantLock();
    private static Lock SHUTDOWN_LOCK = new ReentrantLock();
    private static final int DEFAULT_POOL_SIZE = 32;
    private int maxPoolSize;
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> activeConnections;

    private static Logger logger = LogManager.getLogger();

    private ConnectionPoolImpl() {
    }

    public static ConnectionPoolImpl getInstance() {
        ConnectionPoolImpl localPool = ConnectionPoolImpl.instance;
        if (instance == null) {
            try {
                WORK_LOCK.lock();
                localPool = instance;
                if (localPool == null) {
                    instance = new ConnectionPoolImpl();
                    localPool = instance;
                }
            } finally {
                WORK_LOCK.unlock();
            }
        }
        return localPool;
    }

    public void initConnectionPool() {
        logger.log(Level.INFO, "init connection pool");
        try {
            PoolPropertyManager.INSTANCE.initManager();
            ProxyConnectionCreator.INSTANCE.initCreator();
            maxPoolSize = PoolPropertyManager.INSTANCE
                    .getIntProperty(PROPERTY_POOL_SIZE)
                    .orElse(DEFAULT_POOL_SIZE);
            freeConnections = new ArrayBlockingQueue<>(maxPoolSize);
            activeConnections = new ArrayBlockingQueue<>(maxPoolSize);
            //fill free connections queues
            fillFreeConnectionsQueue();
            isInitialized = true;
        } catch (PoolException e) {
           logger.log(Level.FATAL, "Error while connection pool initializing", e);
           throw new RuntimeException("Error while connection pool initializing", e);
        }
    }

    public ProxyConnection getConnection() throws PoolException {
        if (!isInitialized) {
            logger.log(Level.ERROR, "Error while getting free connection from pool, cause - Pool is not initialized");
            throw new RuntimeException("Error while getting free connection from pool, cause - Pool is not initialized");
        }
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            activeConnections.put(connection);
        } catch (InterruptedException e) {
            throw new PoolException("Error while taking free connection from pool", e);
        }
        return connection;
    }

    /**
     * Check if connection is ProxyConnection was taken from current pool
     */
    public void releaseConnection(ProxyConnection connection) throws PoolException {
        if (!isInitialized) {
            logger.log(Level.ERROR, "Error while releasing connection to pool, cause - Pool is not initialized");
            throw new RuntimeException("Error while releasing connection connection to pool, cause - Pool is not initialized");
        }
        if (connection == null) {
            throw new PoolException("Connection pool ReleaseConnection method received Null ");
        }
        if (connection instanceof ProxyConnection) {
            try {
                WORK_LOCK.lock();
                if (activeConnections.remove(connection)) {
                    freeConnections.add(connection);
                } else {
                    throw new PoolException("Received connection doesn't belong current pool");
                }
            } finally {
                WORK_LOCK.unlock();
            }
        } else {
            throw new PoolException("Received connection doesn't belong current pool: wrong connection type");
        }
    }

    /**
     * @value SHUTDOWN_LOCK
     * Method uses shutdown lock instead work lock
     * to give pool users possibility to release taken connections back to pool
     * Method "take" will be waiting while active connections will be returned back to pool
     */
    public void shutdownPool() throws PoolException {
        if (!isInitialized) {
            logger.log(Level.ERROR, "Error while shutdown pool, cause - Pool is not initialized");
            throw new RuntimeException("Error while shutdown pool, cause - Pool is not initialized");
        }
        ProxyConnection connection;
        try {
            SHUTDOWN_LOCK.lock();
            for (int i = 0; i < maxPoolSize; i++) {
                connection = activeConnections.take();
                if (connection.getAutoCommit()) {
                    connection.rollback();
                }
                connection.closeRealConnection();
            }
        } catch (SQLException | InterruptedException e) {
            throw new PoolException("Error while closing pool connections", e);
        } finally {
            SHUTDOWN_LOCK.unlock();
        }
        ProxyConnectionCreator.INSTANCE.deregisterDBDriver();
        isInitialized = false;
    }

    private void fillFreeConnectionsQueue() throws PoolException {
        for (int i = 0; i < maxPoolSize; i++) {
            ProxyConnection connection = ProxyConnectionCreator.INSTANCE.createConnection();
            freeConnections.add(connection);
        }
    }
}