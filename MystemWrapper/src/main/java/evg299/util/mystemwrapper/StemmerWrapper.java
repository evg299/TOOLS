package evg299.util.mystemwrapper;

import com.google.common.base.Joiner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Evgeny on 22.12.2014.
 */
public class StemmerWrapper {
    private static class StemmerWrapperHolder {
        public static final StemmerWrapper instance = new StemmerWrapper();
    }

    public static StemmerWrapper getInstance() {
        return StemmerWrapperHolder.instance;
    }

    private Properties prop = new Properties();
    private JSONParser parser = new JSONParser();

    private StemmerWrapper() {
        InputStream in = getClass().getResourceAsStream("/mystem.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<WordStem> analysis(Collection<String> words) throws ParseException {
        Set<WordStem> result = new TreeSet<WordStem>();
        List<String> parsed;
        try {
            parsed = mystemPartOfWork(words);
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }


        for (String wordInJson : parsed) {
            JSONObject root = (JSONObject) parser.parse(wordInJson);
            WordStem wordStem = new WordStem();
            for (Object objKey : root.keySet()) {
                String key = (String) objKey;
                if ("text".equals(key)) {
                    String textWord = (String) root.get(key);
                    wordStem.setValue(textWord);
                    continue;
                }

                if ("analysis".equals(key)) {
                    JSONArray jsonArray = (JSONArray) root.get(key);
                    for (Object object : jsonArray) {
                        WordStem.Lexema lexema = new WordStem.Lexema();
                        JSONObject lex = (JSONObject) object;
                        for (Object lexObjKey : lex.keySet()) {
                            String lexKey = (String) lexObjKey;
                            if ("lex".equals(lexKey)) {
                                String textLex = (String) lex.get(lexKey);
                                lexema.setValue(textLex);
                                continue;
                            }

                            if ("gr".equals(lexKey)) {
                                String grLex = (String) lex.get(lexKey);
                                lexema.setGrammemes(convertToGramemes(grLex));
                                continue;
                            }

                            if ("wt".equals(lexKey)) {
                                String wtLex = "" + lex.get(lexKey);
                                lexema.setWeight(Double.parseDouble(wtLex));
                                continue;
                            }
                        }
                        wordStem.getLexemas().add(lexema);
                    }
                }
            }
            result.add(wordStem);
        }
        return result;
    }

    private Set<GrammemeType> convertToGramemes(String grLex) {
        Set<GrammemeType> result = new HashSet<GrammemeType>();
        if (null == grLex)
            return result;

        String[] parts = grLex.split("[,=]");
        for (String part : parts) {
            if (part.startsWith("1") || part.startsWith("2") || part.startsWith("3")) {
                part = "_" + part;
            }

            result.add(GrammemeType.valueOf(part));
        }

        return result;
    }

    public List<String> mystemPartOfWork(Collection<String> words) throws IOException {
        String pathToMystem = prop.getProperty("path_to_mystem");
        String[] commands = new String[]{pathToMystem, "-nid", "--weight", "--eng-gr", "--format", "json", "--generate-all"};
        Process proc = Runtime.getRuntime().exec(commands);
        String input = Joiner.on(' ').skipNulls().join(words);

        PrintWriter pw = new PrintWriter(proc.getOutputStream());
        pw.write(input);
        pw.close();

        List<String> result = new ArrayList<String>();
        InputStream in = proc.getInputStream();
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine())
            result.add(sc.nextLine().trim());
        sc.close();

        return result;
    }
}
