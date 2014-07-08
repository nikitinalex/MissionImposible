package it.sevenbits;

/**
 * Class for exceptions with streams.
 */
public class StreamException extends Exception {
    /**
     * Cause of exception.
     */
    private String msg;

    /**
     * Constructor.
     * @param message is cause of exception
     */
    StreamException(final String message) {
        this.msg = message;
    }

    @Override
    public final String getMessage() {
        return msg;
    }

}
