package it.sevenbits;

import java.io.IOException;

/**
 * Created by alexey on 7/3/14.
 */
public interface Stream {
//    public boolean openStream();
    public boolean closeStream() throws IOException;
}
