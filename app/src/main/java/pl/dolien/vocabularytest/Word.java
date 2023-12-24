package pl.dolien.vocabularytest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Word {
    private List<List<String>> english;
    private List<List<String>> polish;

    // Konstruktor z adnotacją @JsonCreator
    @JsonCreator
    public Word(@JsonProperty("english") List<List<String>> english,
                @JsonProperty("polish") List<List<String>> polish) {
        this.english = english;
        this.polish = polish;
    }

    public List<List<String>> getEnglish() {
        return english;
    }

    public void setEnglish(List<List<String>> english) {
        this.english = english;
    }

    public List<List<String>> getPolish() {
        return polish;
    }

    public void setPolish(List<List<String>> polish) {
        this.polish = polish;
    }
}
