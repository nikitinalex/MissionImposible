package it.sevenbits;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Main class.
 */
public final class Main {
    /**
     * Main method.
     *
     * @param args arguments of command line
     */
    public static void main(final String[] args) {
        PropertyConfigurator.configure(Constants.logFile);
        Logger log = Logger.getLogger(Main.class);


        InStream in = null;
        try {
            in = new FileInStream(args[0]);
        } catch (StreamException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
            return;
        }
        OutStream out = null;
        try {
            out = new FileOutStream(args[1]);
        } catch (StreamException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
            return;
        }


        CodeFormatter f = new CodeFormatter();
        try {
            f.format(in, out);
        } catch (StreamException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
        } catch (FormatterException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
        }
    }

    /**
     * Close constructor.
     */
    private Main() {
    }
}



