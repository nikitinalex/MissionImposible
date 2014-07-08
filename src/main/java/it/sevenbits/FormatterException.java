package it.sevenbits;

/**
 *
 */
public class FormatterException extends Exception {
    private String msg;

    FormatterException(String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
