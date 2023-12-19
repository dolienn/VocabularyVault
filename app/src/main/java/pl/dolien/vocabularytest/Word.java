package pl.dolien.vocabularytest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Word {
    private List<String> english;
    private List<String> polish;


    // Konstruktor z adnotacją @JsonCreator
    @JsonCreator
    public Word(@JsonProperty("english") List<String> english, @JsonProperty("polish") List<String> polish) {
        this.english = english;
        this.polish = polish;
    }

    public List<String> getEnglish() {
        return english;
    }

    public void setEnglish(List<String> english) {
        this.english = english;
    }

    public List<String> getPolish() {
        return polish;
    }

    public void setPolish(List<String> polish) {
        this.polish = polish;
    }
}
