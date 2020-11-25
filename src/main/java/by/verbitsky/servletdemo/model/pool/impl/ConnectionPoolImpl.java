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

/**
 * The implementation of {@link ConnectionPool} interface. It released as Singleton.
 * Method getInstance returns an instance of ConnectionPoolImpl. It uses locks to
 * use a pool in a multithreaded environment.
 * <p>
 * While pool initializing {@link ProxyConnectionCreator} provides {@link ProxyConnection} connections for current pool.
 * Creator uses {@link PoolPropertyManager} to define connection properties.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see PoolPropertyManager
 * @see ProxyConnection
 * @see ProxyConnectionCreator
 * @see ReentrantLock
 */
public class ConnectionPoolImpl implements ConnectionPool<ProxyConnection> {
    /**
     * contains link to single instance
     */
    private static ConnectionPoolImpl instance;
    /**
     * constant to get property pool size from pool property manager
     */
    private static final String PROPERTY_POOL_SIZE = "poolSize";
    /**
     * indicate init status
     */
    private boolean isInitialized;
    /**
     * lock to provide correct methods executing in a multithreaded environment
     */
    private static Lock WORK_LOCK = new ReentrantLock();
    /**
     * lock to provide correct shutdown executing
     */
    private static Lock SHUTDOWN_LOCK = new ReentrantLock();
    /**
     * contains default pool size value
     */
    private static final int DEFAULT_POOL_SIZE = 32;
    /**
     * contains max number of pool connections
     */
    private int maxPoolSize;
    /**
     * queue contains free connections that can be provided by calling method getConnections
     */
    private BlockingQueue<ProxyConnection> freeConnections;
    /**
     * queue contains active connections that used by services
     */
    private BlockingQueue<ProxyConnection> activeConnections;

    private static Logger logger = LogManager.getLogger();

    private ConnectionPoolImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance of pool or create it, if instance is null
     */
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

    /**
     * Init pool: set pool size property, request ProxyConnection creator to create connections
     * and put all connections to free connections queue
     * Set init flag value as true.
     * <p>
     * If init method catches an exception - it throws Runtime exceptions, cause pool couldn't provide connections
     * to data base.
     */
    @Override
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

    @Override
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
     * Check if connection is ProxyConnection and it was taken from current pool
     */
    @Override
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
    @Override
    public void shutdownPool() throws PoolException {
        logger.log(Level.INFO, "Connection Pool shutdown process");
        if (isInitialized) {
            ProxyConnection connection;
            try {
                SHUTDOWN_LOCK.lock();
                for (int i = 0; i < maxPoolSize; i++) {
                    connection = freeConnections.take();
                    if (!connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.closeRealConnection();
                }
            } catch (SQLException | InterruptedException e) {
                logger.log(Level.ERROR, "Connection Pool shutdown fail", e);
                throw new PoolException("Error while closing pool connections", e);
            } finally {
                SHUTDOWN_LOCK.unlock();
            }
            ProxyConnectionCreator.INSTANCE.deregisterDBDriver();
            isInitialized = false;
            logger.log(Level.INFO, "Connection Pool shutdown competed");
        }
    }

    private void fillFreeConnectionsQueue() throws PoolException {
        for (int i = 0; i < maxPoolSize; i++) {
            ProxyConnection connection = ProxyConnectionCreator.INSTANCE.createConnection();
            freeConnections.add(connection);
        }
    }
}