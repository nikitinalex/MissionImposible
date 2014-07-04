package it.sevenbits;

import java.io.*;
import java.util.ArrayList;

/**
 * Using for formatting java-code
 * @author alexey
 *
 */
public class CodeFormatter {
    private String fileName;
    public CodeFormatter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Method reads from input stream, makes code correct and return output
     * stream with correct text
     * @param
     * @return
     * @throws
     *
     */
    public void format() throws FileException {
        LineNumberReader readingFile = null;
        PrintWriter recordFile = null;
        try {
             readingFile = new LineNumberReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String recordFileName = "1";
        recordFileName += fileName;
        try {
            recordFile = new PrintWriter(new BufferedWriter(new FileWriter(recordFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String read = null;
        try {
            //обработка
            ArrayList<String> recordStrings = new ArrayList<String>();
            read = readingFile.readLine();
            while (read != null) {
                recordStrings.add(read);
                read = readingFile.readLine();
            }
            recordStrings = formatSingleString(recordStrings);
            for(String str: recordStrings) {
                recordFile.write(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readingFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        recordFile.close();
    }

    private ArrayList<String> formatSingleString(ArrayList<String> stringsToFormat) {
        ArrayList<String> strings = new ArrayList<String>();
        String newStr = "";

        int countOfTransfer = 0;//количество переносов
        int constructBrackets = 0;//считаем открытые фигурные скобки
        boolean spaceBefore = false;//был ли на предыдущем шаге пробел, изначально не был

        //цикл по всем строкам в коде
        for(String str: stringsToFormat) {
            if (str.equals("\n")) {
                continue;
            }
            //цикл по всем символам в строке
            for (int i = 0; i < str.length(); i++) {
                char parsSymbol = str.charAt(i);
                if (spaceTail(str, i)) {
                    continue;
                }
                if (newStr.equals("")) {
                    newStr = addSpaces(newStr, constructBrackets);
                }
                switch (parsSymbol) {
                    case ';': {
                        newStr = addStringAndUpdate(newStr, ";\n", strings);
                        countOfTransfer++;
                        break;
                    }
                    case ' ': {
                        //если раньше одни пробелы, то не трогать - это отступ
                        if (!stringOnlyWithSpaces(newStr)) {
                            newStr += ' ';
                        }
                        break;
                    }
                    case '\n': {
                        if (countOfTransfer == 0) {
                            newStr = addStringAndUpdate(newStr, "\n", strings);
                            countOfTransfer++;
                        }
                        break;
                    }
                    case '{': {
                        if (!spaceBefore) {
                            newStr += " ";
                        }
                        newStr = addStringAndUpdate(newStr, "{\n", strings);
                        countOfTransfer++;
                        constructBrackets++;
                        break;
                    }
                    case '}': {
                        constructBrackets--;
                        newStr = "";
                        newStr = addSpaces(newStr, constructBrackets);
                        newStr = addStringAndUpdate(newStr, "}\n", strings);
                        countOfTransfer++;
                        break;
                    }
                    case '(': {
                        newStr = addOperAndBracket(newStr, parsSymbol, str, i);
                        break;
                    }
                    case ')': {
                        newStr = addOperAndBracket(newStr, parsSymbol, str, i);
                        break;
                    }
                    case '+': {
                        newStr = addOperAndBracket(newStr, '+', str, i);//addPlusMinus(newStr, '+', str, i);
                        break;
                    }
                    case '-': {
                        newStr = addOperAndBracket(newStr, '-', str, i);//addPlusMinus(newStr, '-', str, i);
                        break;
                    }
                    case '/': {
                        newStr = addOperAndBracket(newStr, '/', str, i);
                        break;
                    }
                    case '*': {
                        newStr = addOperAndBracket(newStr, '*', str, i);
                        break;
                    }
                    default: {
                        newStr += parsSymbol;
                        break;
                    }
                }
                if (!nextTransfer(str, i)) {
                    countOfTransfer = 0;
                }
                if (!newStr.equals("")) {
                    int len = newStr.length();
                    if (newStr.charAt(len - 1) == ' ') {
                        spaceBefore = true;
                    }
                } else {
                    spaceBefore = false;
                }
            }
        }

        return strings;
    }

    private String addPlusMinus(String str, char symb, String fromString, int i) {
        int index = str.length() - 1;
        if(index > 0) {
            if(str.charAt(index) == symb) {
                str += symb + " ";
            } else {
                str += " " + symb;
            }
        }
        if (i != fromString.length() - 1) {
            if(fromString.charAt(i+1) != ' ') {
                str += ' ';
            }
        }
        return str;
    }

    private String addOperAndBracket(String str, char parsSymbol, String fromString, int i) {
        int index = str.length() - 1;
        if (parsSymbol != ')') {
            if (index > 0) {
                if (str.charAt(index) != ' ') {
                    str += " ";
                }
            }
        }
        str += parsSymbol;
        if (parsSymbol != '(') {
            if (i != fromString.length() - 1) {
                if (fromString.charAt(i + 1) != ' ') {
                    str += " ";
                }
            } else {
                str += " ";
            }
        }
        return str;
    }

    //прибавить к строке str строку addition и обновить её на пустую
    private String addStringAndUpdate(String str, String addition, ArrayList<String> strings) {
        str += addition;
        strings.add(str);
        return "";
    }

    private boolean spaceTail(String str, int i) {
        for (int j = i; j < str.length(); j++) {
            if (str.charAt(j) != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean stringOnlyWithSpaces(String str) {
        for (int i = 0; i < str.length(); i++) {
            char curSymb = str.charAt(i);
            if (curSymb != ' ' && curSymb != '\n') {
                return false;
            }
        }
        return true;
    }

    private String addSpaces(String str, int spaces) {
        for (int i = 0; i < spaces; i++) {
            str += "    ";
        }
        return str;
    }

    private boolean nextTransfer(String str, int i) {
        for (int j = i; j < str.length(); j++) {
            char symb = str.charAt(j);
            if (symb != ' ' && symb != '\n') {
                return false;
            }
            if (symb == '\n') {
                return true;
            }
        }
        return false;
    }
}

class FileException extends RuntimeException {
    private String msg;
    public FileException(String couse) {
        msg = couse;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
