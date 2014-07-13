package it.sevenbits;

import org.apache.log4j.Logger;

/**
 * Class for stream or cover for String.
 */
public class StringOutStream implements OutStream {
    /**
     * Stream itself.
     */
    private StringBuilder outStringStream;
    /**
     * Size of Stream.
     */
    private int streamSize;
    /**
     * Checks on closure.
     */
    private boolean isClosed = false;
    /**
     * How many symbols in stream already.
     */
    private int pointer = 0;
    /**
     * Makes log records.
     */
    private Logger log = null;


    /**
     * Constructor.
     * @param size Size of stream
     */
    public StringOutStream(final int size) {
    //согласно сигнатуре не надо выбрасывать
    // исключения,но неплохо бы это делать
    // на случай, если size отрицателен
        log = Logger.getLogger(StringOutStream.class);
        if (size > 0) {
            outStringStream = new StringBuilder(size);
            streamSize = size;
        } else {
            log.error("Wrong size");
        }
    }

    @Override
    public final void writeSymbol(final char b) throws StreamException {
        if (outStringStream == null || isClosed) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }
        if (pointer != streamSize) {
            outStringStream.append(b);
            pointer++;
        }
    }

    @Override
    public final void close() throws StreamException {
        if (outStringStream == null) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }
        isClosed = true;
        return;
    }

    @Override
    public final String toString() {
        return outStringStream.toString();
    }
}
