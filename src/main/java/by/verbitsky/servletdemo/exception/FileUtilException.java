package by.verbitsky.servletdemo.exception;

/**
 * Class FileUtil exception
 * FileException object could be thrown by FileUtil while processing file or folder operation
 * <p>
 *
 * @see by.verbitsky.servletdemo.util.FileUtil
 */
@SuppressWarnings("serial")
public class FileUtilException extends Exception {
    public FileUtilException() {
        super();
    }

    public FileUtilException(String message) {
        super(message);
    }

    public FileUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUtilException(Throwable cause) {
        super(cause);
    }
}