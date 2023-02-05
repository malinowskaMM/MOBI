package com.example.poznajpowiedzenia.quiz;

import java.util.List;

public class Question {
    String title;
    List<String> incorrect;
    String correct;

    public Question(String title, List<String> incorrect, String correct) {
        this.title = title;
        this.incorrect = incorrect;
        this.correct = correct;
    }

    public String getTitle() {
        return title;
    }

    public String getCorrect() {
        return correct;
    }

    public List<String> getIncorrect() {
        return incorrect;
    }

}
