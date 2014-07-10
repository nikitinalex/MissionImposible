package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
    private FileInputStream fileStream;

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
        PropertyConfigurator.configure(Constants.logFile);
        log = Logger.getLogger(FileInStream.class);
        if (newFileName == null) {
            String msg = "File is not available or corrupted";
            log.error(msg);
            throw new StreamException(msg);
        }
        try {
            fileStream = new FileInputStream(newFileName);
        } catch (FileNotFoundException e) {
            String msg = "File is not available or corrupted";
            log.error(msg);
            throw new StreamException(msg);
        }
    }

    @Override
    public final char getSymbol() throws StreamException {
        char res;
        try {
            res = (char) fileStream.read();
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        return res;
    }

    @Override
    public final  boolean isEnd() throws StreamException {
        try {
            if (fileStream.available() > 0) {
                return false;
            }
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
        return true;
    }

    @Override
    public final void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }
}
