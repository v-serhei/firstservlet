package by.verbitsky.servletdemo.model.pool.impl;

import by.verbitsky.servletdemo.exception.PoolException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConnectionPoolImplTest {

    @Test
    public void testGetInstance() {
        ConnectionPoolImpl instance = ConnectionPoolImpl.getInstance();
        Assert.assertNotNull(instance);
    }

    @Test
    public void testGetConnectionPositive() throws PoolException {
        ConnectionPoolImpl instance = ConnectionPoolImpl.getInstance();
        instance.initConnectionPool();
        ProxyConnection connection = instance.getConnection();
        Assert.assertNotNull(connection);
        instance.releaseConnection(connection);
        instance.shutdownPool();
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void testShutdownPool() throws PoolException {
        ConnectionPoolImpl instance = ConnectionPoolImpl.getInstance();
        instance.initConnectionPool();
        instance.shutdownPool();
        instance.getConnection();
    }
}