package it.sevenbits;


/**
 * Main class
 */
public class TextMaker {
    public static void main(String[] args) {
        InStream in = new FileInStream(args[0]);
        OutStream out = new FileOutStream(args[1]);
        CodeFormatter f = new CodeFormatter();
        f.format(in, out);
    }
}



