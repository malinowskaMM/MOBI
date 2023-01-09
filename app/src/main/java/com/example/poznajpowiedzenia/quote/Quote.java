package com.example.poznajpowiedzenia.quote;


public class Quote {
    String title;
    String meaning;

    public Quote(String title, String meaning) {
        this.title = title;
        this.meaning = meaning;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getTitle() {
        return title;
    }

    public String getMeaning() {
        return meaning;
    }
}
