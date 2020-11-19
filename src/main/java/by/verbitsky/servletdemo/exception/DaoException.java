package by.verbitsky.servletdemo.exception;

/**
 * Class Dao exception
 * DaoException object could be thrown by Dao while processing SQL query
 * <p>
 *
 * @see by.verbitsky.servletdemo.model.dao.BaseDao
 * @see by.verbitsky.servletdemo.model.dao.ContentDao
 * @see by.verbitsky.servletdemo.model.dao.OrderDao
 * @see by.verbitsky.servletdemo.model.dao.UserDao
 */
@SuppressWarnings("serial")
public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
