package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;

import java.sql.Connection;

/**
 * Class AbstractDao defines common Dao field and method setConnection realization
 * <p>
 *
 * @author Verbitsky Sergey
 * @version 1.0
 */
public abstract class AbstractDao {
    /**
     * contains connection with application data base
     */
    protected Connection connection;

    /**
     * Sets connection as field of BaseDao impls that used to process operations with data base
     *
     * @param connection - contains connection with data base
     * @throws DaoException the dao exception if received null, cause user query couldn't be processed without connection
     */
    public void setConnection(Connection connection) throws DaoException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new DaoException("SetConnection: received null connection");
        }
    }
}
