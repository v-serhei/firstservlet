package by.verbitsky.servletdemo.exception;

@SuppressWarnings("serial")
public class FileUtilException extends Exception{
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