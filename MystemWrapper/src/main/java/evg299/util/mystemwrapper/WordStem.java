package evg299.util.mystemwrapper;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Evgeny on 22.12.2014.
 */
public class WordStem implements Comparable, Serializable{

    public int compareTo(Object o) {
        if (this == o) return 0;
        if (!(o instanceof WordStem)) return 0;

        WordStem wordStem = (WordStem) o;

        return this.value.compareTo(wordStem.value);
    }

    public static class Lexema implements Serializable {
        private String value;
        private double weight;
        private Set<GrammemeType> grammemes = new HashSet<GrammemeType>();

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public Set<GrammemeType> getGrammemes() {
            return grammemes;
        }

        public void setGrammemes(Set<GrammemeType> grammemes) {
            this.grammemes = grammemes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Lexema)) return false;

            Lexema lexema = (Lexema) o;

            if (value != null ? !value.equals(lexema.value) : lexema.value != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Lexema{" +
                    "value='" + value + '\'' +
                    ", weight=" + weight +
                    ", grammemes=" + grammemes +
                    '}';
        }
    }

    private String value;
    private Set<Lexema> lexemas = new HashSet<Lexema>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Lexema> getLexemas() {
        return lexemas;
    }

    public void setLexemas(Set<Lexema> lexemas) {
        this.lexemas = lexemas;
    }

    @Override
    public String toString() {
        return "WordStem{" +
                "value='" + value + '\'' +
                ", lexemas=" + lexemas +
                '}';
    }
}
