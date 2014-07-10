package it.sevenbits;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * The class is used for java-code formatting.
 * @author Alexey Nikitin
 */
public class CodeFormatter {
    /**
     * @value offsetSize  size of offset from begin of string
     */
    private final int offsetSize = 4;
    /**
     * @value offsetSymb  symbol for using offset
     */
    private final char offsetSymb = ' ';

    /**
     * It makes logs.
     */
    private Logger log = null;

    /**
     * Default constructor.
     */
    public CodeFormatter() {
        PropertyConfigurator.configure(Constants.logFile);
        log = Logger.getLogger(CodeFormatter.class);
    }

    /**
     * Method reads symbols from input stream, transform them
     * to the correct style of java-code and push result to the
     * output stream.
     * @param  in  Input Stream
     * @param out Output Stream
     * @throws StreamException if rises some problem with input or
     * output streams
     * @throws FormatterException if rises incorrect symbols
     */
    public final void format(final InStream in, final OutStream out)
            throws StreamException, FormatterException {
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

        char curSymb = 0;
        boolean transBefore = false;
        boolean forBefore = false;
        boolean preparedFor = false;
        int offset = 0;
        int roundBrackets = 0;
        String bufString = new String("");

        try {
            while (!in.isEnd()) {
                try {
                    curSymb = in.getSymbol();
                } catch (StreamException e) {
                    in.close();
                    out.close();
                    String msg = "Symbol trouble";
                    log.error(msg);
                    throw new FormatterException(msg);
                }
                switch (curSymb) {
                    case ' ':
                        if (preparedFor) {
                            forBefore = true;
                            preparedFor = false;
                        }
                        if (transBefore) {
                            continue;
                        }
                        bufString += ' ';
                        break;
                    case ';':
                        if (!forBefore) {
                            transBefore = true;
                            bufString += ';';
                            bufString = recordInStream(bufString, out, offset);
                            continue;
                        } else {
                            bufString += ";";
                        }
                        break;
                    case '{':
                        offset++;
                        transBefore = true;
                        bufString += '{';
                        bufString = recordInStream(bufString, out, offset);
                        continue;
                    case '}':
                        if (!isEmpty(bufString)) {
                            recordInStream(bufString, out, offset);
                        }
                        bufString = "";
                        offset--;
                        if (offset < 0) {
                            String msg = "Too much close brackets";
                            log.error(msg);
                            throw new NotEnoughBracketsException(msg);
                        }
                        transBefore = true;
                        bufString = addSpaces(bufString, offset);
                        bufString += '}';
                        bufString = recordInStream(bufString, out, offset);
                        continue;
                    case '(':
                        if (preparedFor) {
                            forBefore = true;
                            preparedFor = false;
                        }
                        roundBrackets++;
                        bufString += '(';
                        break;
                    case ')':
                        roundBrackets--;
                        if (forBefore && roundBrackets == 0) {
                            forBefore = false;
                        }
                        bufString += ')';
                        break;
                    case '\r':
                        continue;
                    case '\n':
                        if (transBefore) {
                            continue;
                        }
                        transBefore = true;
                        bufString = recordInStream(bufString, out, offset);
                        continue;
                    case 'r':
                        preparedFor = isFor(bufString);
                        bufString += 'r';
                        break;
                    default:
                        bufString += curSymb;
                        break;
                }
                if (curSymb != '\n' && transBefore) {
                    transBefore = false;
                }

            }
            if (offset > 0) {
                String msg = "Too much open brackets";
                log.error(msg);
                throw new NotEnoughBracketsException(msg);
            }
        } finally {
            in.close();
            out.close();
        }
        if (!bufString.equals("")) {
            out.writeString(bufString);
        }

    }

    /**
     * Checks if in string only offsetSymb and '\n'.
     * @param str string for check
     * @return true if is str only offsetSymb
     */
    private boolean isEmpty(final String str) {
        for (int i = 0; i < str.length(); i++) {
            char symb = str.charAt(i);
            if (symb != offsetSymb && symb != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * Records String in output stream and adds offset to the next string.
     * @param str input string
     * @param stream output stream
     * @param offset quantity of offset
     * @return string with spaces
     * @throws StreamException if rises some problem with output stream
     */
    private String recordInStream(final String str, final OutStream stream,
                                  final int offset) throws StreamException {
        String rec = str;
        rec += '\n';
        stream.writeString(rec);
        rec = "";
        rec = addSpaces(rec, offset);
        return rec;
    }

    /**
     * Checks on control construction in the end of input string.
     * @param str input string
     * @return true if last 2-3 symbols is "fo" or " fo"
     * 3 is not magic number
     */
    private boolean isFor(final String str) {
        //Если for начинается с начала строки без
        // пробелов
        int fromBegin = 2;
        //Если перед for стоит пробел
        int spaceBefore = 3;
        if (str.length() < fromBegin) {
            return false;
        }
        int len = str.length();
        String s = str.substring(len - fromBegin, len);
        if (s.equals("fo") && len == fromBegin) {
            return true;
        }
        s = str.substring(len - spaceBefore, len);
        if (len > fromBegin) {
            if (s.equals(" fo")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add offset symbols to input string.
     * @param str input string
     * @param offset amount of offset
     * @return new string
     */
    private String addSpaces(final String str, final int offset) {
        String rec = str;
        for (int i = 0; i < this.offsetSize * offset; i++) {
            rec += this.offsetSymb;
        }
        return rec;
    }
}
