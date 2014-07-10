import it.sevenbits.*;
import org.junit.Assert;
import org.junit.Test;

public class FormatterTests {

    @Test
    public void classDeclaration() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream("class Enough{}");
        OutStream out = new StringOutStream(20);
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "class Enough{\n}\n";
        Assert.assertEquals("Not equals", expected, actually);
    }

    @Test
    public void classWithMethods() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream("class AnotherOneClass{public int field;" +
                "\nprotected boolean testMethod   (){int k++;}}");
        OutStream out = new StringOutStream(200);
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "class AnotherOneClass{\n    public int field;\n    protected boolean" +
                " testMethod   (){\n        int k++;\n    }\n}\n";
        Assert.assertEquals("Not equals", expected, actually);
    }

    @Test
    public void forCheck() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream("for(int i=0;i<10;i++){ for(int j = 0; j<10;j++)i++;}");
        OutStream out = new StringOutStream(200);
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
        }
        String actually = out.toString();
        String expected = "for(int i=0;i<10;i++){\n    for(int j = 0; j<10;j++)i++;\n}\n";
        Assert.assertEquals("Not equals", expected, actually);
    }

    @Test
    public void alrightBrackets() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream("{{{{}}}}");
        OutStream out = new StringOutStream(200);
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
        }
        Assert.assertEquals("Problems", "{\n    {\n        {\n            {\n" +
                "            }\n        }\n    }\n}\n", out.toString());
    }

    @Test
    public void nullTest() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream(null);
        OutStream out = new StringOutStream(20);
        try {
            f.format(in, out);
        } catch (StreamException e) {
            Assert.assertEquals("Wrong negative test", Constants.streamIsNotAvailable, e.getMessage());
        } catch (FormatterException e) {
        }
        out = null;
        try {
            f.format(in, out);
        } catch (StreamException e) {
            Assert.assertEquals("Wrong negative test", "Null output stream", e.getMessage());
        } catch (FormatterException e) {
        }
    }

    @Test
    public void wrongBrackets() {
        CodeFormatter f = new CodeFormatter();
        InStream in = new StringInStream("{{{{");
        OutStream out = new StringOutStream(20);
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
            Assert.assertEquals("Brackets alright", "Too much open brackets", e.getMessage());
        }
        in = new StringInStream("{{{{{}}}}}}");
        try {
            f.format(in, out);
        } catch (StreamException e) {
        } catch (FormatterException e) {
            Assert.assertEquals("Brackets alright", "Too much close brackets", e.getMessage());
        }
    }
}
