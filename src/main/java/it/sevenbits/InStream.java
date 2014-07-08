package it.sevenbits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

/**
 * Input Stream based on file.
 */
class FileInStream implements InStream {
    /**
     * fileName Name of file stream.
     */
    private String fileName;
    /**
     * fileStream Stream itself.
     */
    private FileInputStream fileStream;

    /**
     * Constructing stream by file name.
     * @param newFileName - name of file for stream
     */
    public FileInStream(final String newFileName) throws StreamException {
        this.fileName = newFileName;
        try {
            fileStream = new FileInputStream(newFileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("File is not available or corrupted");
        }
    }

    @Override
    public char getSymbol() throws StreamException {
        char res;
        try {
            res = (char) fileStream.read();
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
        }
        return res;
    }

    @Override
    public boolean isEnd() throws StreamException {
        try {
            if (fileStream.available() > 0) {
                return true;
            }
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
        }
        return false;
    }

    @Override
    public void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
        }
    }
}

/**
 * Input Stream based on single string.
 */
class StringInStream implements InStream {
    /**
     * streamString Stream itself.
     */
    private String streamString;
    /**
     * @value pointer Pointer on current symbol in string stream
     */
    private int pointer = 0;

    private boolean isClose = false;

    /**
     * Constructor.
     * @param streamStr Stream itself
     */
    public StringInStream(final String streamStr) {
        this.streamString = streamStr;
    }

    @Override
    public char getSymbol() throws StreamException {
        if (streamString == null || isClose) {
            throw new StreamException("Stream is not available");
        }

        if (pointer != streamString.length()) {
            return streamString.charAt(pointer);
        }
        return (char) -1;
    }

    @Override
    public boolean isEnd() throws StreamException {
        if (streamString == null || isClose) {
            throw new StreamException("Stream is not available");
        }

        if (pointer == streamString.length()) {
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        isClose = true;
        return;
    }
}

