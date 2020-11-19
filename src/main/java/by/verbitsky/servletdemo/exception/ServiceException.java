package by.verbitsky.servletdemo.exception;

/**
 * Class Service exception
 * ServiceException object could be thrown by ContentService impls, OrderService impl or UserService impl
 * while processing user request
 * <p>
 *
 * @see by.verbitsky.servletdemo.model.service.ContentService
 * @see by.verbitsky.servletdemo.model.service.OrderService
 * @see by.verbitsky.servletdemo.model.service.UserService
 */

@SuppressWarnings("serial")
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
