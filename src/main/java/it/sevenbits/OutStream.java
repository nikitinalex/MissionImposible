package it.sevenbits;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Interface for output stream.
 */
public interface OutStream extends Stream {
    /**
     * Writes symbol into stream.
     * @param newSymb Symbol for record
     */
    void recordSymbol(char newSymb);
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
    public FileOutStream(final String newFileName) {
        this.fileName = newFileName;
        try {
            fileStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new FileProblem("Problems with output file, check the name");
        }
    }

    /**
     * Getter.
     * @return name of file
     */
    public String getFileName() {
        return fileName;
    }


    @Override
    public boolean closeStream() throws IOException {
        if (fileStream == null) {
            return false;
        }
        fileStream.close();
        return true;
    }

    @Override
    public void recordSymbol(final char newSymb) {
        try {
            fileStream.write((int) newSymb);
        } catch (IOException e) {
            throw new FileProblem("It doesn't recording");
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
     * Pointer on current symbol in stream.
     */
    private int pointer;

    /**
     * Constructor.
     * @param workingStream Stream
     */
    public StringOutStream(final StringBuilder workingStream) {
        outStringStream = workingStream;
        pointer = outStringStream.length() - 1;
    }

    @Override
    public void recordSymbol(final char newSymb) {
        outStringStream.append(newSymb);
        pointer++;
    }

    @Override
    public boolean closeStream() throws IOException {
        return true;
    }
}
