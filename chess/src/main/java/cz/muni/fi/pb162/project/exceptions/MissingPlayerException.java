package cz.muni.fi.pb162.project.exceptions;

/**
 * Missing player exception
 * @author Alex Popovic
 */
public class MissingPlayerException extends RuntimeException {
    /**
     * Creates new exception
     */
    public MissingPlayerException() {
    }
    /**
     * Creates new exception
     * @param message message
     */
    public MissingPlayerException(String message) {
        super(message);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     */
    public MissingPlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception
     * @param cause cause
     */
    public MissingPlayerException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     * @param enableSuppression suppression
     * @param writableStackTrace stack trace
     */
    public MissingPlayerException(String message, Throwable cause,
                                  boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
