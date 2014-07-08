package it.sevenbits;

import java.io.IOException;

/**
 * Interface for any Streams.
 */

public interface Stream {
    /**
     * Closes Stream.
     * @return true if close is success
     * @throws IOException
     */
    boolean closeStream() throws IOException;
}

class InputStreamException extends RuntimeException {
    private String msg;

    public InputStreamException(final String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

class OutputStreamException extends RuntimeException {
    private String msg;

    public OutputStreamException(final String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

    /**
    * Class exception using for problems with file streams.
    */
class FileProblem extends RuntimeException {
    /**
     * Message of problem.
     */
    private String msg;

    /**
     * Constructor.
     * @param message Problem message
     */
    public FileProblem(final String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
