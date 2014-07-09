package it.sevenbits;

/**
 * Class exception for formatter actions.
 */
public class FormatterException extends Exception {
    /**
     * Message of cause of problem.
     */
    private String msg;

    /**
     * Constructor.
     * @param message cause of current problem
     */
    FormatterException(final String message) {
        this.msg = message;
    }

    @Override
    public final String getMessage() {
        return msg;
    }
}
