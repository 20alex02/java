package cz.muni.fi.pb162.project.exceptions;

/**
 * Not allowed move exception
 * @author Alex Popovic
 */
public class NotAllowedMoveException extends Exception {
    /**
     * Creates new exception
     */
    public NotAllowedMoveException() {
    }

    /**
     * Creates new exception
     * @param message message
     */
    public NotAllowedMoveException(String message) {
        super(message);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     */
    public NotAllowedMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception
     * @param cause cause
     */
    public NotAllowedMoveException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     * @param enableSuppression suppression
     * @param writableStackTrace stack trace
     */
    public NotAllowedMoveException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
