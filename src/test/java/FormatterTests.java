import it.sevenbits.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FormatterTests extends Assert {
    private CodeFormatter formatter;

    @Before
    public void Constructor() {
        formatter = new CodeFormatter();
    }

    @Test
    public void classDeclaration() throws StreamException {
        InStream in = new StringInStream("class Enough{}");
        OutStream out = new StringOutStream(20);
        try {
            formatter.format(in, out);
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "class Enough{\n}\n";
        assertEquals("Not equals", expected, actually);
    }

    @Test
    public void classWithMethods() throws StreamException {
        InStream in = new StringInStream("class AnotherOneClass{public int field;" +
                "\nprotected boolean testMethod   (){int k++;}}");
        OutStream out = new StringOutStream(200);
        try {
            formatter.format(in, out);
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "class AnotherOneClass{\n    public int field;\n    protected boolean" +
                " testMethod   (){\n        int k++;\n    }\n}\n";
        assertEquals("Not equals", expected, actually);
    }

    @Test
    public void forCheck() throws StreamException{
        InStream in = new StringInStream("for(int i=0;i<10;i++){ for(int j = 0; j<10;j++)i++;}");
        OutStream out = new StringOutStream(200);
        try {
            formatter.format(in, out);
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "for(int i=0;i<10;i++){\n    for(int j = 0; j<10;j++)i++;\n}\n";
        assertEquals("Not equals", expected, actually);
    }

    @Test
    public void alrightBrackets() throws StreamException {
        InStream in = new StringInStream("{{{{}}}}");
        OutStream out = new StringOutStream(200);
        try {
            formatter.format(in, out);
        } catch (FormatterException e) {
        }
        assertEquals("Problems", "{\n    {\n        {\n            {\n" +
                "            }\n        }\n    }\n}\n", out.toString());
    }

    @Test(expected = FormatterException.class)
    public void nullInTest() throws FormatterException, StreamException {
        InStream in = new StringInStream(null);
        OutStream out = new StringOutStream(20);
        formatter.format(in, out);
    }

    @Test(expected = FormatterException.class)
    public void nullOutTest() throws FormatterException {
        InStream in = new StringInStream("123");
        OutStream out = null;
        formatter.format(in, out);
    }

    @Test(expected = NotEnoughBracketsException.class)
    public void wrongOpenBrackets() throws FormatterException, StreamException {
        InStream in = new StringInStream("{{{{");
        OutStream out = new StringOutStream(20);
        formatter.format(in, out);
    }

    @Test(expected = NotEnoughBracketsException.class)
    public void wrongCloseBrackets() throws FormatterException, StreamException {
        InStream in = new StringInStream("{{{{{}}}}}}");
        OutStream out = new StringOutStream(20);
        formatter.format(in, out);
    }
}
