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

