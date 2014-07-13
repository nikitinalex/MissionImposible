package it.sevenbits;

import org.apache.log4j.Logger;

/**
 * Input Stream based on single string.
 */
public class StringInStream implements InStream {
    /**
     * streamString Stream itself.
     */
    private String streamString;
    /**
     * @value pointer Pointer on current symbol in string stream
     */
    private int pointer = 0;

    /**
     * Checks on closure of stream.
     */
    private boolean isClose = false;

    /**
     * Makes log records.
     */
    private Logger log = null;

    /**
     * Constructor.
     * @param newStreamString Stream itself
     */
    public StringInStream(final String newStreamString) {
        log = Logger.getLogger(StringInStream.class);
        this.streamString = newStreamString;
    }

    @Override
    public final char getSymbol() throws StreamException {
        if (streamString == null || isClose) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }

        if (pointer != streamString.length()) {
            char result = streamString.charAt(pointer);
            pointer++;
            return result;
        }
        return (char) -1;
    }

    @Override
    public final boolean isEnd() throws StreamException {
        if (streamString == null || isClose) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }

        if (pointer == streamString.length()) {
            return true;
        }
        return false;
    }

    @Override
    public final void close() {
        isClose = true;
        return;
    }
}
