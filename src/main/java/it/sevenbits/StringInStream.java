package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
     * @param streamStr Stream itself
     */
    public StringInStream(final String streamStr) {
        PropertyConfigurator.configure(Constants.logFile);
        log = Logger.getLogger(StringInStream.class);
        this.streamString = streamStr;
    }

    @Override
    public final char getSymbol() throws StreamException {
        if (streamString == null || isClose) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }

        if (pointer != streamString.length()) {
            char res = streamString.charAt(pointer);
            pointer++;
            return res;
        }
        return (char) -1;
    }

    @Override
    public final boolean isEnd() throws StreamException {
        if (streamString == null || isClose) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
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
