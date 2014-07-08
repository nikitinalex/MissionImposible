package it.sevenbits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface for input stream.
 */
public interface InStream extends Stream {
    /**
     * Reads one symbol.
     * @return symbol from stream or -1 if the end of file is distinguished
     */
    char getSymbol();
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
    public FileInStream(final String newFileName) {
        this.fileName = newFileName;
        try {
            fileStream = new FileInputStream(newFileName);
        } catch (FileNotFoundException e) {
            throw new FileProblem("File can not be opened");
        }
    }

    /**
     * Getter for name of file stream.
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public char getSymbol() {
        char res;
        try {
            res = (char) fileStream.read();

        } catch (IOException e) {
            throw new FileProblem("Can not read this file");
        }
        return res;
    }

    @Override
    public boolean closeStream() {
        if (fileStream != null) {
            try {
                fileStream.close();
            } catch (IOException e) {
                throw new FileProblem("Stream can not be close");
            }
            return true;
        }
        return false;
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

    /**
     * Constructor.
     * @param streamStr Stream itself
     */
    public StringInStream(final String streamStr) {
        this.streamString = streamStr;
    }

    @Override
    public char getSymbol() {
        if (pointer != streamString.length()) {
            return streamString.charAt(pointer);
        }
        return (char) -1;
    }

    @Override
    public boolean closeStream() {
        return true;
    }
}

