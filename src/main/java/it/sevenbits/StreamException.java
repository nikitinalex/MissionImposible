package it.sevenbits;

/**
 * Class for exceptions with streams.
 */
public class StreamException extends Exception {
    /**
     * Constructor.
     * @param message is cause of exception
     */
    StreamException(final String message) {
        super(message);
    }

    StreamException(final Exception e) {
        super(e);
    }

}
