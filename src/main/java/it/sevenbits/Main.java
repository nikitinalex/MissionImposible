package it.sevenbits;


import org.apache.log4j.Logger;


/**
 * Main class.
 */
public final class Main {
    /**
     * Main method.
     *
     * @param args arguments of command line
     * 1st parameter is name of input file
     * 2nd parameter is name of output file
     */
    public static void main(final String[] args) {
        Logger log = Logger.getLogger(Main.class);

        if (args == null) {
            System.out.println("The input and output files aren't specified");
            log.error("Arguments is null");
            return;
        }
        if (args.length < 2) {
            System.out.println("Not enough parameters or this applications");
            log.error("Not enough program arguments");
            return;
        }

        InStream inputFileStream = null;
        try {
            inputFileStream = new FileInStream(args[0]);
        } catch (StreamException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
            return;
        }
        OutStream outputFileStream = null;
        try {
            outputFileStream = new FileOutStream(args[1]);
        } catch (StreamException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
            return;
        }


        CodeFormatter formatter = new CodeFormatter();
        try {
            formatter.format(inputFileStream, outputFileStream);
        } catch (FormatterException e) {
            String msg = e.getMessage();
            System.out.println(msg);
            log.error(msg);
        }
        try {
            inputFileStream.close();
        } catch (StreamException e) {
            System.out.println("Problem with closing input file");
            log.error(e.getMessage());
        }
        try {
            outputFileStream.close();
        } catch (StreamException e) {
            System.out.println("Problem with closing output file");
            log.error(e.getMessage());
        }
    }

    /**
     * Close constructor.
     */
    private Main() {
    }
}



