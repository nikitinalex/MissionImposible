package it.sevenbits;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Input Stream based on file.
 */
public class FileInStream implements InStream {
    /**
     * fileName Name of file stream.
     */
    private String fileName;
    /**
     * fileStream Stream itself.
     */
    private BufferedInputStream fileStream;

    /**
     * Makes log records.
     */
    private Logger log = null;

    /**
     * Constructing stream by file name.
     * @param newFileName - name of file for stream
     * @throws it.sevenbits.StreamException if name is null or file
     * doesn't exists or something like that
     */
    public FileInStream(final String newFileName) throws StreamException {
        this.fileName = newFileName;
        log = Logger.getLogger(FileInStream.class);
        if (newFileName == null) {
            throw new StreamException("File is not available or corrupted");
        }
        try {
            fileStream = new BufferedInputStream(
                new FileInputStream(newFileName));
        } catch (FileNotFoundException e) {
            log.error("File is not available or corrupted");
            throw new StreamException(e);
        }
    }

    @Override
    public final char getSymbol() throws StreamException {
        char result;
        try {
            result = (char) fileStream.read();
        } catch (IOException e) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(e);
        }
        return result;
    }

    @Override
    public final  boolean isEnd() throws StreamException {
        try {
            if (fileStream.available() > 0) {
                return false;
            }
        } catch (IOException e) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(e);
        }
        return true;
    }

    @Override
    public final void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(e);
        }
    }
}
