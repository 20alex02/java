package cz.muni.fi.pb162.project.exceptions;

/**
 * Empty square exception
 * @author Alex Popovic
 */
public class EmptySquareException extends Exception {
    /**
     * Creates new exception
     */
    public EmptySquareException() {
    }
    /**
     * Creates new exception
     * @param message message
     */
    public EmptySquareException(String message) {
        super(message);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     */
    public EmptySquareException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception
     * @param cause cause
     */
    public EmptySquareException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     * @param enableSuppression suppression
     * @param writableStackTrace stack trace
     */
    public EmptySquareException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
