package by.verbitsky.servletdemo.exception;

@SuppressWarnings("serial")
public class AudioBoxException extends Exception{
    public AudioBoxException() {
        super();
    }

    public AudioBoxException(String message) {
        super(message);
    }

    public AudioBoxException(String message, Throwable cause) {
        super(message, cause);
    }

    public AudioBoxException(Throwable cause) {
        super(cause);
    }
}