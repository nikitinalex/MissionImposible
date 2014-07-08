/**
 * Having class for formatting code
 */
package it.sevenbits;

import java.io.IOException;

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
     * Method reads symbols from input stream, transform them
     * to the correct style of java-code and push result to the
     * output stream.
     * @param  in  Input Stream
     * @param out Output Stream
     */
    public void format(final InStream in, final OutStream out) {
        if (in == null) {
            throw new InputStreamException("Null input stream");
        }
        if (out == null) {
            throw new OutputStreamException("Null output stream");
        }

        char curSymb;
        boolean transBefore = false;
        boolean forBefore = false;
        boolean preparedFor = false;
        int offset = 0;
        int roundBrackets = 0;
        String bufString = new String("");

        while ((curSymb = in.getSymbol()) != (char) -1) {
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
        if (bufString.equals("")) {
            recordString(bufString, out);
        }
        try {
            in.closeStream();
        } catch (IOException e) {
            throw new InputStreamException("Problem with closure input stream");
        }
        try {
            out.closeStream();
        } catch (IOException e) {
            throw new OutputStreamException("Problem with closure output "
                    + "stream");
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
     */
    private String recordInStream(final String str, final OutStream stream,
                                  final int offset) {
        String rec = str;
        rec += '\n';
        recordString(rec, stream);
        rec = "";
        rec = addSpaces(str, offset);
        return rec;
    }

    /**
     * Checks on control construction in the end of input string.
     * @param str input string
     * @return true if last 2-3 symbols is "fo" or " fo"
     */
    private boolean isFor(final String str) {
        if (str.length() < 2) {
            return false;
        }
        int len = str.length();
        String s = str.substring(len - 2, len);
        if (s.equals("fo") && len == 2) {
            return true;
        }
        if (len > 2) {
            if (s.equals(" fo")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Records string into stream.
     * @param str input string
     * @param out stream
     */
    private void recordString(final String str, final OutStream out) {
        for (int i = 0; i < str.length(); i++) {
            out.recordSymbol(str.charAt(i));
        }
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


/**
 * Class for problems with files.
 */
class FileException extends RuntimeException {
    /**
     * Message with cause of problem.
     */
    private String msg;

    /**
     * Constructor.
     * @param couse New cause of problem
     */
    public FileException(final String couse) {
        msg = couse;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
