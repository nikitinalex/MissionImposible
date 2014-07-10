package it.sevenbits;

/**
 * Interface for input stream.
 */
public interface InStream  {
    /**
     * Reads one symbol.
     * @return symbol from stream or -1 if the end of file is distinguished
     * @throws StreamException if stream is not available
     */
    char getSymbol() throws StreamException;

    /**
     * Checks on end of stream.
     * @return true if it is the end
     * @throws StreamException if stream is not available
     */
    boolean isEnd() throws StreamException;

    /**
     * Checks stream on closure.
     * @throws StreamException if stream is not available
     */
    void close() throws StreamException;
}

