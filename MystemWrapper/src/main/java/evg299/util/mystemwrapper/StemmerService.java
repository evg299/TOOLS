package evg299.util.mystemwrapper;

import org.json.simple.parser.ParseException;

import java.util.*;

/**
 * Created by Evgeny on 25.12.2014.
 */
public class StemmerService {
    private static class StemmerServiceHolder {
        public static final StemmerService instance = new StemmerService();
    }

    public static StemmerService getInstance() {
        return StemmerServiceHolder.instance;
    }

    private StemmerService() {
    }

    private StemmerWrapper stemmerWrapper = StemmerWrapper.getInstance();
    private Map<String, WordStem> cash = new TreeMap<String, WordStem>();

    public WordStem analysisWord(String s) {
        if (null == s)
            return null;

        s = s.toLowerCase();
        if (cash.containsKey(s))
            return cash.get(s);


        List<String> strings = new ArrayList<String>();
        strings.add(s);
        try {
            Set<WordStem> wordStems = stemmerWrapper.analysis(strings);
            WordStem wordStem = wordStems.iterator().next();
            cash.put(wordStem.getValue(), wordStem);
            return wordStem;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<WordStem> analysisWords(Collection<String> strings) {
        Set<WordStem> result = new HashSet<WordStem>();
        Set<String> buffer = new TreeSet<String>();
        for (String s : strings) {
            if (null == s)
                continue;

            s = s.toLowerCase();
            if (cash.containsKey(s)) {
                result.add(cash.get(s));
                continue;
            }

            buffer.add(s);
        }

        try {
            Set<WordStem> wordStems = stemmerWrapper.analysis(buffer);
            for (WordStem wordStem : wordStems) {
                cash.put(wordStem.getValue(), wordStem);
            }
            result.addAll(wordStems);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
