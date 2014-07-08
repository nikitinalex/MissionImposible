package it.sevenbits;


import com.sun.deploy.util.SessionState;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Main class.
 */
public class TextMaker {
    /**
     * Main method.
     * @param args arguments of command line
     */
    public static void main(final String[] args) {

        InStream in = new FileInStream(args[0]);
        OutStream out = new FileOutStream(args[1]);

        PropertyConfigurator.configure("log4j.properties");
        Logger log = Logger.getLogger(TextMaker.class);


        log.info("message");
        CodeFormatter f = new CodeFormatter();
        f.format(in, out);

    }
}



