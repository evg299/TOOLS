package evg299.util.mystemwrapper;

import junit.framework.Assert;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Evgeny on 24.12.2014.
 */
public class StemmerWrapperTest {
    @Test
    public void analysisTest() throws ParseException {
        List<String> line = new ArrayList<String>();
        line.add("программы");

        Set<WordStem> wordStems = StemmerWrapper.getInstance().analysis(line);
        WordStem wordStem = wordStems.iterator().next();

        Assert.assertEquals("программы", wordStem.getValue());

        Set<WordStem.Lexema> lexemas = wordStem.getLexemas();
        WordStem.Lexema lexema = lexemas.iterator().next();

        Assert.assertEquals("программа", lexema.getValue());
        Assert.assertEquals(1.0, lexema.getWeight());
        Assert.assertEquals(true, lexema.getGrammemes().contains(GrammemeType.S));
    }
}
