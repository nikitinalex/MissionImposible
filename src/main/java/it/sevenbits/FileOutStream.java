package it.sevenbits;

import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for output stream by file.
 */
public class FileOutStream implements OutStream {

    /**
     * fileName Name of file.
     */
    private String fileName;
    /**
     * fileStream Stream itself.
     */
    private BufferedOutputStream fileStream;

    /**
     * Makes log records.
     */
    private Logger log = null;

    /**
     * Constructor by the name.
     * @param newFileName name of file
     * @throws it.sevenbits.StreamException is file is not found
     */
    public FileOutStream(final String newFileName) throws StreamException {
        this.fileName = newFileName;
        log = Logger.getLogger(FileOutStream.class);
        try {
            fileStream = new BufferedOutputStream(
                    new FileOutputStream(this.fileName));
        } catch (FileNotFoundException e) {
            String msg = "File is not available or corrupted";
            log.error(msg);
            throw new StreamException(msg);
        }
    }

    @Override
    public final void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }
    }

    @Override
    public final void writeSymbol(final char b) throws StreamException {
        try {
            fileStream.write((int) b);
        } catch (IOException e) {
            log.error(Constants.STREAM_IS_NOT_AVAILABLE);
            throw new StreamException(Constants.STREAM_IS_NOT_AVAILABLE);
        }
    }
}
