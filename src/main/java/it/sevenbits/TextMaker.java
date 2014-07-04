package it.sevenbits;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by alexey on 7/3/14.
 */
public class TextMaker {

    public static void main(String[] args) {
//        CodeFormatter f = new CodeFormatter(args[0]);
//        f.format();
        StringBuilder str = new StringBuilder("000");
        some s = new some(str);
        s.add();
        System.out.println(str + " " + s.getStr());
    }
}

class some {
    private StringBuilder str;

    public some(StringBuilder newStr) {
        str = newStr;
    }

    public void add() {
        str.append('4');
    }

    public StringBuilder getStr() {
        return str;
    }
}




