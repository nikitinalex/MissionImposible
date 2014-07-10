package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
        PropertyConfigurator.configure(Constants.logFile);
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
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        if (pointer != streamSize) {
            outStringStream.append(b);
            pointer++;
        }
    }

    @Override
    public final void writeString(final String str) throws StreamException {
    //почему-то этот метод есть в двух
    // имплементируемых классах, но нет
    // в интерфейсе OutStream
        if (str == null) {
            String msg = "Output string is empty";
            log.error(msg);
            throw new StreamException(msg);
        }
        if (outStringStream == null) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        for (int i = 0; i < str.length(); i++) {
            if (pointer != streamSize) {
                outStringStream.append(str.charAt(i));
                pointer++;
            } else {
                break;
            }
        }
    }

    @Override
    public final void close() throws StreamException {
        if (outStringStream == null) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        isClosed = true;
        return;
    }

    @Override
    public final String toString() {
        return outStringStream.toString();
    }
}
