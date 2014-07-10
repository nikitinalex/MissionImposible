package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
    private FileOutputStream fileStream;

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
    public final void writeString(final String str) throws StreamException {
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
    public final void close() throws StreamException {
        try {
            fileStream.close();
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }

    @Override
    public final void writeSymbol(final char b) throws StreamException {
        try {
            fileStream.write((int) b);
        } catch (IOException e) {
            log.error(Constants.streamIsNotAvailable);
            throw new StreamException(Constants.streamIsNotAvailable);
        }
    }
}
