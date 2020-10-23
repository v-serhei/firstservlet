package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.pool.impl.ProxyConnection;

import java.sql.SQLException;

public class Transaction implements AutoCloseable {
    private final ProxyConnection connection;

    public Transaction(ProxyConnection connection) throws DaoException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new DaoException("Transaction: received null connection");
        }
    }

    public void processSimpleQuery(BaseDao dao) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQL exception while processing simple query");
            }
            dao.setConnection(connection);
        } else {
            throw new DaoException("Transaction: ProcessSingleRequest error: null connection");
        }
    }

    public void processTransaction(BaseDao... daos) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQLException while processing transaction", e);
            }
            for (BaseDao dao : daos) {
                dao.setConnection(connection);
            }
        } else {
            throw new DaoException("Transaction: ProcessSingleRequest error: null connection");
        }
    }

    public void rollbackTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQLException while processing rollback operation", e);
            }
        } else {
            throw new DaoException("Transaction: Rollback error: null connection");
        }
    }

    public void commitTransaction() throws DaoException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQLException while processing commit operation", e);
            }
        } else {
            throw new DaoException("Transaction: Commit error: null connection");
        }
    }

    @Override
    public void close() throws DaoException {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQLException while closing transaction", e);
            } finally {
                connection.close();
            }
        }
    }
}