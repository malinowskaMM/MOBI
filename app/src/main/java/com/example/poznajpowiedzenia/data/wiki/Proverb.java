package com.example.poznajpowiedzenia.data.wiki;

public class Proverb {
    private String title;
    private String meaning;

    public Proverb(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
    }

    public String getTitle() {
        return title;
    }

    public String getMeaning() {
        return meaning;
    }
}
