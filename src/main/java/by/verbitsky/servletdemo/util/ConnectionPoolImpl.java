package by.verbitsky.servletdemo.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolImpl {
    private static volatile ConnectionPoolImpl instance;
    //path to property resource
    private static final String DB_PROPERTIES_FILE = "db/dbconnection.properties";
    //db connection path in properties
    private static final String PROPERTY_DB_URL = "db.url";
    private static final String PROPERTY_POOL_SIZE = "poolSize";
    private static final int DEFAULT_CONNECTION_VALIDATION_TIME = 1;
    private static final String SQL_VERIFY_QUERY = "select 1";
    private final Properties properties = new Properties();
    private final Object MONITOR = new Object();
    private int maxPoolSize;
    private BlockingQueue<Connection> freePool;
    private BlockingQueue<Connection> activePool;

    private ConnectionPoolImpl() {
        //todo дописать логи
        System.out.println("Start init poll");
    }

    public static ConnectionPoolImpl getInstance() {
        ConnectionPoolImpl localPool = ConnectionPoolImpl.instance;
        if (instance == null) {
            synchronized (ConnectionPoolImpl.class) {
                localPool = instance;
                if (localPool == null) {
                    instance = new ConnectionPoolImpl();
                    localPool = instance;
                }
            }
        }
        return localPool;
    }

    public void initConnectionPool() {
        InputStream propertyFileInputStream = getClass().getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE);
        try {
            properties.load(propertyFileInputStream);
            System.out.println("db url:" + (properties.getProperty(PROPERTY_DB_URL)));
            maxPoolSize = Integer.parseInt(properties.getProperty(PROPERTY_POOL_SIZE));
            freePool = new ArrayBlockingQueue<>(maxPoolSize);
            activePool = new ArrayBlockingQueue<>(maxPoolSize);
            List<Connection> connections = createConnections();
            freePool.addAll(connections);
        } catch (IOException e) {
            //todo заменить на вызов страницы с ошибкой
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        int count = freePool.size() + activePool.size();
        if (freePool.size() > 0) {
            connection = verifyConnection(freePool.poll());
        } else {
            if (count < maxPoolSize) {
                connection = createConnection();
            } else {
                try {
                    connection = verifyConnection(freePool.take());
                } catch (InterruptedException e) {
                    //todo log this
                    e.printStackTrace();
                }
            }
        }
        activePool.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            if (activePool.remove(connection)) {
                try {
                    if (connection.isValid(DEFAULT_CONNECTION_VALIDATION_TIME)) {
                        freePool.add(connection);
                    } else {
                        Connection updatedConnection = createConnection();
                        freePool.add(updatedConnection);
                    }
                } catch (SQLException throwables) {
                    //log
                    throwables.printStackTrace();
                }
            } else {
                //todo log this
            }
        }
    }

    public void rebuildPoolConnections() {
        synchronized (MONITOR) {
            shutdownAllConnections();
            List<Connection> connections = createConnections();
            freePool.addAll(connections);
        }
    }

    private void shutdownAllConnections() {
        for (Connection connection : activePool) {
            try {
                if (connection != null) {
                    if (connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.close();
                }
            } catch (SQLException throwables) {
                //ignore this or log...
            }
        }
        for (Connection connection : freePool) {
            if (connection != null) {
                try {
                    if (connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.close();
                } catch (SQLException throwables) {
                    //ignore this or log...
                }
            }
        }
        activePool.clear();
        freePool.clear();
    }

    private void shutdownConnection(Connection connection) {
        synchronized (MONITOR) {
            if (freePool.remove(connection)) {
                try {
                    if (connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.close();
                } catch (SQLException throwables) {
                    //ignore this or log...
                }
                try {
                    if (connection.getAutoCommit()) {
                        connection.rollback();
                    }
                    connection.close();
                } catch (SQLException throwables) {
                    //ignore this or log...
                }
            }
        }
    }

    private Connection verifyConnection(Connection connection) {
        Connection result;
        if (connection == null) {
            result = createConnection();
        } else {
            try (Statement testStatement = connection.createStatement()) {
                testStatement.executeQuery(SQL_VERIFY_QUERY);
                result = connection;
            } catch (SQLException e) {
                shutdownConnection(connection);
                result = createConnection();
            }
        }
        return result;
    }

    private List<Connection> createConnections() {
        List<Connection> connections = new ArrayList<>();
        int count;
        if (maxPoolSize < 10) {
            count = maxPoolSize;
        } else {
            count = maxPoolSize / 2;
        }
        for (int i = 0; i < count; i++) {
            connections.add(createConnection());
        }
        return connections;
    }

    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getProperty(PROPERTY_DB_URL), properties);
        } catch (SQLException throwables) {
            //todo log this
            throwables.printStackTrace();
        }
        return connection;
    }
}
