package it.sevenbits;

import org.apache.log4j.Logger;


/**
 * The class is used for java-code formatting.
 * @author Alexey Nikitin
 */
public class CodeFormatter {
    /**
     * It makes logs.
     */
    private Logger log = null;

    /**
     * Default constructor.
     */
    public CodeFormatter() {
        log = Logger.getLogger(CodeFormatter.class);
    }

    /**
     * Method reads symbols from input stream, transform them
     * to the correct style of java-code and push result to the
     * output stream.
     *
     * @param in  Input Stream
     * @param out Output Stream
     * @throws FormatterException if rises incorrect symbols
     */
    public final void format(final InStream in, final OutStream out)
        throws FormatterException {
        if (in == null) {
            String msg = "Null input stream";
            log.error(msg);
            throw new FormatterException(msg);
        }
        if (out == null) {
            String msg = "Null output stream";
            log.error(msg);
            throw new FormatterException(msg);
        }
        final int offsetSize = Constants.OFFSET_SIZE;
        final char offsetSymbol = Constants.OFFSET_SYMBOL;
        char currentSymbol;
        boolean transferBefore = false;
        //checks symbols on right position
        //construction "for"
        boolean[] forCheck = new boolean[Constants.FOR_LENGTH];
        //First acceptable symbol before "for"
        forCheck[0] = true;
        boolean beginOfStream = true;
        boolean forBefore = false;
        int offset = 0;
        int roundBrackets = 0;
        try {
            while (!in.isEnd()) {
                currentSymbol = in.getSymbol();
                if (currentSymbol == '\r') {
                    continue;
                }
                if (transferBefore) {
                    if (currentSymbol != ' ' && currentSymbol != '}'
                        && currentSymbol != '\n') {
                        addSpaces(out, offset, offsetSize, offsetSymbol);
                    }
                }
                if (beginOfStream) {
                    if (currentSymbol == ' ') {
                        continue;
                    }
                    beginOfStream = false;
                }
                switch (currentSymbol) {
                    case ' ':
                        if (isForBefore(forCheck, Constants.FOR_LENGTH)) {
                            forBefore = true;
                        }
                        forCheck[0] = true;
                        if (transferBefore) {
                            continue;
                        }
                        out.writeSymbol(' ');
                        break;
                    case ';':
                        out.writeSymbol(';');
                        if (!forBefore) {
                            out.writeSymbol('\n');
                            transferBefore = true;
                            continue;
                        }
                        forCheck[0] = true;
                        break;
                    case '{':
                        forCheck[0] = true;
                        offset++;
                        out.writeSymbol('{');
                        out.writeSymbol('\n');
                        transferBefore = true;
                        continue;
                    case '}':
                        forCheck[0] = true;
                        offset--;
                        if (offset < 0) {
                            throw new NotEnoughBracketsException("Too much close brackets");
                        }
                        if (!transferBefore) {
                            out.writeSymbol('\n');
                        }
                        addSpaces(out, offset, offsetSize, offsetSymbol);
                        out.writeSymbol('}');
                        out.writeSymbol('\n');
                        transferBefore = true;
                        continue;
                    case '(':
                        if (isForBefore(forCheck, Constants.FOR_LENGTH)) {
                            forBefore = true;
                        }
                        roundBrackets++;
                        out.writeSymbol('(');
                        break;
                    case ')':
                        forCheck[0] = true;
                        roundBrackets--;
                        if (forBefore && roundBrackets == 0) {
                            for (int i = 0; i < Constants.FOR_LENGTH; i++) {
                                forCheck[i] = false;
                            }
                            forBefore = false;
                        }
                        out.writeSymbol(')');
                        break;
                    case '\n':
                        if (transferBefore) {
                            continue;
                        }
                        forCheck[0] = true;
                        out.writeSymbol('\n');
                        transferBefore = true;
                        continue;
                    case 'f':
                        checkConstructionSymbolAndRecord(
                            forCheck, 1, out, 'f');
                        break;
                    case 'o':
                        checkConstructionSymbolAndRecord(
                            forCheck, 2, out, 'o');
                        break;
                    case 'r':
                        checkConstructionSymbolAndRecord(
                            forCheck, Constants.FOR_LENGTH - 1, out, 'r');
                        break;
                    default:
                        out.writeSymbol(currentSymbol);
                        break;
                }
                if (currentSymbol != '\n' && transferBefore) {
                    transferBefore = false;
                }
            }
        } catch (StreamException e) {
            String msg = e.getMessage();
            log.error(msg);
            throw new FormatterException(msg);
        }
        if (offset > 0) {
            throw new NotEnoughBracketsException("Too much open brackets");
        }
    }

    /**
     * If piece of "for"-construction previously then
     * change next position on true.
     * @param boolMas massive of correct positions
     * @param length place to change value in boolMas
     * @param out stream for write
     * @param c write symbol
     */
    private void checkConstructionSymbolAndRecord(
        final boolean[] boolMas, final int length, final OutStream out,
        final char c) throws StreamException {
        if (isForBefore(boolMas, length)) {
            boolMas[length] = true;
        }
        out.writeSymbol(c);
    }


    /**
     * Checks construction "for".
     * @param mas checks position of characters
     * @param length length which need to check (1, 2, 3 or 4)
     * @return true if in mas all values is true
     */
    private boolean isForBefore(final boolean[] mas, final int length) {
        for (int i = 0; i < length; i++) {
            if (!mas[i]) {
                for (int j = 0; j < i; j++) {
                    mas[j] = false;
                }
                return false;
            }
        }
        return true;
    }

    /**
     * Add offset symbols to stream.
     * @param stream stream for record
     * @param offset amount of offset
     * @throws it.sevenbits.StreamException is stream
     * cannot record some character or stream is null
     */
    private void addSpaces(final OutStream stream, final int offset,
        final int offsetSize, final char offsetSymbol)
            throws StreamException {
        for (int i = 0; i < offset * offsetSize; i++) {
            stream.writeSymbol(offsetSymbol);
        }
    }
}
