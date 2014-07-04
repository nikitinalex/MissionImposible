package it.sevenbits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface for input stream
 */
public interface InStream extends Stream {
    public char getSymbol();
}

/**
 * Input Stream based on file
 */
class FileInStream implements InStream {
    private String fileName;
    private FileInputStream fileStream;

    /**
     * Constructing stream by file name
     * @param fileName - file name
     */
    public FileInStream(String fileName) {
        this.fileName = fileName;
        try {
            fileStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new FileProblem("File can not be opened");
        }
    }

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
 * Input Stream based on single string
 */
class StringInStream implements InStream {
    private String streamString;
    private int pointer = 0;

    public StringInStream(String streamString) {
        this.streamString = streamString;
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

class FileProblem extends RuntimeException {
    private String msg;

    public FileProblem(String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
