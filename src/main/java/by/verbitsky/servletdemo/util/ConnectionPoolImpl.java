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
    private static final Properties properties = new Properties();
    private static final String PROPERTY_DB_URL = "db.url";
    private static final String PROPERTY_POOL_SIZE = "poolSize";
    private static final int DEFAULT_CONNECTION_VALIDATION_TIME = 1;
    private static final String SQL_VERIFY_QUERY = "select 1";
    private boolean poolInitializationState;
    private int poolSize;
    private BlockingQueue<Connection> freePool;
    private BlockingQueue<Connection> activePool;
    /*

    db.driver=com.mysql.cj.jdbc.Driver
    db.url = jdbc:mysql://localhost:3306/web_application
    encoding = UTF-8
    useJDBCCompliantTimezoneShift = true
    password = webroot
    poolSize = 32
    serverTimezone = UTC
    useLegacyDatetimeCode = false
    user = webapp
    useUnicode = true
    * */


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

    public void initConnectionPool(InputStream stream) {
        try {
            properties.load(stream);
        } catch (IOException e) {
            //todo заменить на вызов страницы с ошибкой
            e.printStackTrace();
        }
        poolSize = Integer.parseInt(properties.getProperty(PROPERTY_POOL_SIZE));

        freePool = new ArrayBlockingQueue<>(poolSize);
        activePool = new ArrayBlockingQueue<>(poolSize);
        List<Connection> connectionList = createConnections();
        if (connectionList != null) {
            freePool.addAll(createConnections());
            poolInitializationState = true;
        }
    }

    public Connection getConnection() {
        int count = activePool.size() + freePool.size();
        Connection connection = null;
        if (freePool.size() > 0) {
            connection = verifyConnection(activePool.poll());
        } else {
            if (count < poolSize) {
                connection = createConnection();
            } else {
                try {
                    connection = verifyConnection(activePool.take());
                } catch (InterruptedException e) {
                    //todo log this
                    e.printStackTrace();
                }
            }
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean result = false;
        if (connection != null) {
            if (activePool.remove(connection)) {
                try {
                    if (connection.isValid(DEFAULT_CONNECTION_VALIDATION_TIME)) {
                        freePool.add(connection);
                    } else {
                        Connection updatedConnection = createConnection();
                        freePool.add(updatedConnection);
                    }
                    result = true;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                //todo log this
            }
        }
        return result;
    }

    private Connection verifyConnection(Connection connection) {
        Connection result = connection;
        try (Statement st = connection.createStatement()) {
            st.executeQuery(SQL_VERIFY_QUERY);
        } catch (SQLException e) {
            result = createConnection();
        }
        return result;
    }


    private List<Connection> createConnections() {
        List<Connection> connections = new ArrayList<>();
        int count;
        if (poolSize < 10) {
            count = poolSize;
        } else {
            count = poolSize / 2;
        }
        for (int i = 0; i < count; i++) {
            connections.add(createConnection());
        }
        return connections;
    }

    private Connection createConnection() {
        Connection connection = null;
        if (isPoolInitializationState()) {
            try {
                connection = DriverManager.getConnection(properties.getProperty(PROPERTY_DB_URL), properties);
            } catch (SQLException throwables) {
                //todo log this
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    public boolean isPoolInitializationState() {
        return poolInitializationState;
    }
}
