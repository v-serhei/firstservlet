package by.verbitsky.servletdemo.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import by.verbitsky.servletdemo.pool.DBConnectionCreator;
import by.verbitsky.servletdemo.pool.IConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool implements IConnectionPool<ProxyConnection> {
    private static volatile ConnectionPool instance;
    private static final Lock WORK_LOCK = new ReentrantLock();
    private static final Lock SHUTDOWN_LOCK = new ReentrantLock();
    private static final String PROPERTY_POOL_SIZE = "poolSize";
    private static final String PROPERTY_FILE = "db/dbconnection.properties";
    private static Logger logger = LogManager.getLogger();
    private int maxPoolSize;
    private int currentConnectionsCount;
    private boolean isActive;
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> activeConnections;
    private DBConnectionCreator<ProxyConnection> provider;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localPool = ConnectionPool.instance;
        if (instance == null) {
            try {
                WORK_LOCK.lock();
                localPool = instance;
                if (localPool == null) {
                    instance = new ConnectionPool();
                    localPool = instance;
                }
            } finally {
                WORK_LOCK.unlock();
            }
        }
        return localPool;
    }

    public void initConnectionPool(DBConnectionCreator<ProxyConnection> connectionProvider) {
        logger.log(Level.INFO, "Connection pool initializing");
        try (InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
            provider = connectionProvider;
            Properties properties = new Properties();
            properties.load(propertyFileInputStream);
            maxPoolSize = Integer.parseInt(properties.getProperty(PROPERTY_POOL_SIZE));
            freeConnections = new ArrayBlockingQueue<>(maxPoolSize);
            activeConnections = new ArrayBlockingQueue<>(maxPoolSize);
            currentConnectionsCount = 0;
            isActive = true;
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error to load connection properties", e);
            throw new RuntimeException("Error to load connection properties", e);
        }
    }

    /**
     * Check free connections count. If free connections queue is empty
     * check active connection queue.
     * If active connections count = max pool size - client should wait for free connection,
     * else create new connection, add it to active
     */
    public ProxyConnection getConnection() throws PoolException {
        if (!isActive) {
            logger.log(Level.ERROR, "Error while getting free connection from pool, cause - Pool is not initialized");
            throw new RuntimeException("Error while getting free connection from pool, cause - Pool is not initialized");
        }
        ProxyConnection connection = null;
        try {
            WORK_LOCK.lock();
            if (freeConnections.size() == 0) {
                if (activeConnections.size() < maxPoolSize) {
                    connection = provider.createConnection();
                    //add new connection to active connections queue
                    activeConnections.add(connection);
                    currentConnectionsCount++;
                }
            } else {
                connection = freeConnections.take();
                activeConnections.add(connection);
            }
        } catch (InterruptedException | PoolException e) {
            throw new PoolException("Error while taking free connection from pool", e);
        } finally {
            WORK_LOCK.unlock();
        }
        return connection;
    }

    /**
     * Check if connection is ProxyConnection was taken from current pool
     */
    public void releaseConnection(ProxyConnection connection) throws PoolException {
        if (connection instanceof ProxyConnection) {
            try {
                WORK_LOCK.lock();
                if (activeConnections.contains(connection)) {
                    activeConnections.remove(connection);
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
     * Method take will be waiting while active connections will be returned back to pool
     */
    public void shutdownPool() throws PoolException {
        ProxyConnection connection;
        try {
            SHUTDOWN_LOCK.lock();
            for (int i = 0; i < currentConnectionsCount; i++) {
                if (currentConnectionsCount > maxPoolSize) {
                    throw new PoolException("Shutdown pool: Wrong connections count in pool");
                }
                connection = activeConnections.take();
                if (connection.getAutoCommit()) {
                    connection.rollback();
                }
               connection.closeRealConnection();
            }
            provider.deregisterDBDriver();
        } catch (SQLException | InterruptedException e) {
            logger.log(Level.ERROR, "Error while shutdown pool", e);

        } finally {
            SHUTDOWN_LOCK.unlock();
        }
    }
}