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
    NotEnoughBracketsException(String message) {
        super(message);
    }
}
