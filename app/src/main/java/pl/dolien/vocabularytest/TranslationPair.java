package pl.dolien.vocabularytest;

import java.util.List;
import java.util.Objects;

public class TranslationPair {
    private List<List<String>> source;
    private List<List<String>> target;

    public TranslationPair(List<List<String>> source, List<List<String>> target) {
        this.source = source;
        this.target = target;
    }

    // Getters and setters (if needed)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationPair that = (TranslationPair) o;
        return Objects.equals(source, that.source) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}

