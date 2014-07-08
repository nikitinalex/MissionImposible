package it.sevenbits;

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
     */
    void writeSymbol(char b) throws StreamException;
    void writeString(String str) throws StreamException;
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
     * Constructor by the name.
     * @param newFileName name of file
     */
    public FileOutStream(final String newFileName) throws StreamException {
        this.fileName = newFileName;
        try {
            fileStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new StreamException("File is not available or corrupted");
        }
    }

    @Override
    public void writeString (String str) throws StreamException {
        if (str == null) {
            throw new StreamException("Output string is empty");
        }
        try {
            for (int i = 0; i < str.length(); i++) {
                char recSym = str.charAt(i);
                fileStream.write((int) recSym);
            }
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
        }
    }

    @Override
    public void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
        }
    }

    @Override
    public void writeSymbol(final char b) throws StreamException {
        try {
            fileStream.write((int) b);
        } catch (IOException e) {
            throw new StreamException("Stream is not available");
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
    private int streamSize;
    private boolean isClosed = false;

    /**
     * Constructor.
     * @param size Size of stream
     */
    public StringOutStream(final int size) {
        //согласно сигнатуре не надо выбрасывать исключения,
        //но неплохо бы это делать на случай, если size отрицателен
        if (size > 0) {
            outStringStream = new StringBuilder(size);
        }
    }

    @Override
    public void writeSymbol(final char b) throws StreamException {
        if (outStringStream == null || isClosed) {
            throw new StreamException("Stream is not available");
        }
        outStringStream.append(b);
    }

    @Override
    public void writeString(final String str) throws StreamException {
        //почему-то этот метод есть в двух имплементируемых
        //классах, но нет в интерфейсе OutStream
        if (str == null) {
            throw new StreamException("Output string is empty");
        }
        if (outStringStream == null) {
            throw new StreamException("Stream is not available");
        }
        outStringStream.append(str);
    }

    @Override
    public void close() throws StreamException {
        if (outStringStream == null) {
            throw new StreamException("Stream is not available");
        }
        isClosed = true;
        return;
    }

    @Override
    public String toString() {
        return outStringStream.toString();
    }
}
