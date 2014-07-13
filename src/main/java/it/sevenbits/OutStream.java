package it.sevenbits;

/**
 * Interface for output stream.
 */
public interface OutStream {
    /**
     * Writes symbol into stream.
     * @param b Symbol for record
     * @throws StreamException is stream is end or close
     * or null.
     */
    void writeSymbol(char b) throws StreamException;

    /**
     * Closes stream.
     * @throws StreamException if null
     */
    void close() throws StreamException;
}

