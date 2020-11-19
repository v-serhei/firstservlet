package by.verbitsky.servletdemo.exception;

/**
 * Class Pool exception
 * PoolException object could be thrown by ConnectionPoolInstance
 * <p>
 *
 * @see by.verbitsky.servletdemo.model.pool.impl.ConnectionPoolImpl
 */
@SuppressWarnings("serial")
public class PoolException extends Exception {
    public PoolException() {
        super();
    }

    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolException(Throwable cause) {
        super(cause);
    }
}
