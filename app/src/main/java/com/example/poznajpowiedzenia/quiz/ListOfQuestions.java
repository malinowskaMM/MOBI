package com.example.poznajpowiedzenia.quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfQuestions implements Serializable {
    private List<Question> questionList = new ArrayList<>();
    private int numberOfQuestion;
    private int numberOfCorrectAnswers;
    public List<Question> questionList() {
        return questionList;
    }

    public ListOfQuestions(List<Question> questionList, int numberOfQuestion, int numberOfCorrectAnswers) {
        this.questionList = questionList;
        this.numberOfQuestion = numberOfQuestion;
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }
}
