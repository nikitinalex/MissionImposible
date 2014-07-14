package it.sevenbits;

/**
 * Raises when there is brackets mismatch.
 */
public class NotEnoughBracketsException extends FormatterException {
    /**
     * Constructor.
     *
     * @param message cause of current problem
     */
    public NotEnoughBracketsException(final String message) {
        super(message);
    }

    public NotEnoughBracketsException(final Exception e) {
        super(e);
    }
}
