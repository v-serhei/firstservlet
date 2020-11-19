package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;
import by.verbitsky.servletdemo.model.pool.impl.ProxyConnection;

import java.sql.SQLException;

/**
 * Class Transaction provides methods to manage SQL transactions
 * <p>
 * Implements AutoCloseable interface to provide usage Transaction objects in try with resources.
 * It automatically calls method close on connection and rolls back transaction if received SQL exception
 * while processing SQL query.
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 * @see BaseDao
 * @see <a href="https://en.wikipedia.org/wiki/Database_transaction">Sql transactions</a>
 */
public class Transaction implements AutoCloseable {
    private final ProxyConnection connection;

    /**
     * Constructor
     *
     * @param connection contains connection with application data base
     * @throws DaoException the dao exception if received null
     */
    public Transaction(ProxyConnection connection) throws DaoException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new DaoException("Transaction: received null connection");
        }
    }

    /**
     * Process simple query: query running in the autocommit "true" mode
     *
     * @param dao the dao object
     * @throws DaoException the dao exception if received Sql exception while processing query, or current connection is null
     */
    public void processSimpleQuery(BaseDao<?> dao) throws DaoException {
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

    /**
     * Process transaction: Process simple query: query running in the autocommit "false" mode.
     * If dao throws exception - transaction will be rolled back
     *
     * @param daos array of Dao objects
     * @throws DaoException the dao exception if received Sql exception while processing query, or current connection is null
     */
    public void processTransaction(BaseDao<?>... daos) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoException("Transaction: received SQLException while processing transaction", e);
            }
            for (BaseDao<?> dao : daos) {
                dao.setConnection(connection);
            }
        } else {
            throw new DaoException("Transaction: ProcessSingleRequest error: null connection");
        }
    }

    /**
     * Rollback transaction
     *
     * @throws DaoException the dao exception if received Sql exception while processing query, or current connection is null
     */
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

    /**
     * Commit transaction.
     *
     * @throws DaoException the dao exception if received Sql exception while processing query, or current connection is null
     */
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