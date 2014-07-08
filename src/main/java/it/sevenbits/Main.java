package it.sevenbits;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Main class.
 */
public class Main {
    /**
     * Main method.
     * @param args arguments of command line
     */
    public static void main(final String[] args) {

        InStream in = null;
        try {
            in = new FileInStream(args[0]);
        } catch (StreamException e) {
            System.out.println(e.getMessage());
            return;
        }
        OutStream out = null;
        try {
            out = new FileOutStream(args[1]);
        } catch (StreamException e) {
            System.out.println(e.getMessage());
            return;
        }

        PropertyConfigurator.configure("log4j.properties");
        Logger log = Logger.getLogger(Main.class);


        log.info("message");
        CodeFormatter f = new CodeFormatter();
        try {
            f.format(in, out);
        } catch (StreamException e) {
            System.out.println(e.getMessage());
        } catch (FormatterException e) {
            System.out.println(e.getMessage());
        }

    }
}



