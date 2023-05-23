package cz.muni.fi.pb162.project.exceptions;

/**
 * Invalid format exception
 * @author Alex Popovic
 */
public class InvalidFormatOfInputException extends RuntimeException {
    /**
     * Creates new exception
     */
    public InvalidFormatOfInputException() {
    }
    /**
     * Creates new exception
     * @param message message
     */
    public InvalidFormatOfInputException(String message) {
        super(message);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     */
    public InvalidFormatOfInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception
     * @param cause cause
     */
    public InvalidFormatOfInputException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates new exception
     * @param message message
     * @param cause cause
     * @param enableSuppression suppression
     * @param writableStackTrace stack trace
     */
    public InvalidFormatOfInputException(String message, Throwable cause,
                                         boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
