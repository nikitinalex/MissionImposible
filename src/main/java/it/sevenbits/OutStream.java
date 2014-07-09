package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
     * Records string into the stream.
     * @param str string for record
     * @throws StreamException if null or some another problems
     */
    void writeString(String str) throws StreamException;

    /**
     * Closes stream.
     * @throws StreamException if null
     */
    void close() throws StreamException;
}

/**
 * Class for output stream by file.
 */
class FileOutStream implements OutStream {

    /**
     * fileName Name of file.
     */
    private String fileName;
    /**
     * fileStream Stream itself.
     */
    private FileOutputStream fileStream;

    /**
     * Makes log records.
     */
    private Logger log = null;

    /**
     * Constructor by the name.
     * @param newFileName name of file
     * @throws StreamException is file is not found
     */
    public FileOutStream(final String newFileName) throws StreamException {
        this.fileName = newFileName;
        PropertyConfigurator.configure(Constants.logFile);
        log = Logger.getLogger(FileOutStream.class);
        try {
            fileStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            String msg = "File is not available or corrupted";
            log.error(msg);
            throw new StreamException(msg);
        }
    }

    @Override
    public void writeString(final String str) throws StreamException {
        if (str == null) {
            String msg = "Output string is empty";
            log.error(msg);
            throw new StreamException(msg);
        }
        try {
            for (int i = 0; i < str.length(); i++) {
                char recSym = str.charAt(i);
                fileStream.write((int) recSym);
            }
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }

    @Override
    public void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }

    @Override
    public void writeSymbol(final char b) throws StreamException {
        try {
            fileStream.write((int) b);
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }
}

/**
 * Class for stream or cover for String.
 */
class StringOutStream implements OutStream {
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
        } else {
            log.error("Wrong size");
        }
    }

    @Override
    public void writeSymbol(final char b) throws StreamException {
        if (outStringStream == null || isClosed) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        if (pointer != streamSize) {
            outStringStream.append(b);
        }
    }

    @Override
    public void writeString(final String str) throws StreamException {
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
            } else {
                break;
            }
        }
    }

    @Override
    public void close() throws StreamException {
        if (outStringStream == null) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        isClosed = true;
        return;
    }

    @Override
    public String toString() {
        return outStringStream.toString();
    }
}
