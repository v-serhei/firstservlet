package by.verbitsky.servletdemo.model.dao;

import by.verbitsky.servletdemo.exception.DaoException;

import java.sql.Connection;

public abstract class AbstractDao {
    protected Connection connection;

    public  void setConnection(Connection connection) throws DaoException {
        if (connection != null) {
            this.connection = connection;
        } else {
            throw new DaoException("SetConnection: received null connection");
        }
    }
}
