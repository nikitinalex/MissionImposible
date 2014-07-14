package it.sevenbits;

/**
 * Class exception for formatter actions.
 */
public class FormatterException extends Exception {

    /**
     * Constructor.
     * @param message cause of current problem
     */
    public FormatterException(final String message) {
        super(message);
    }

    public FormatterException(final Exception e) {
        super(e);
    }
}
