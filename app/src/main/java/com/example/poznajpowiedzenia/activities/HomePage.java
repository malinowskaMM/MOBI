package com.example.poznajpowiedzenia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.controller.AppController;
import com.example.poznajpowiedzenia.data.wiki.Proverb;
import com.example.poznajpowiedzenia.quiz.ListOfQuestions;
import com.example.poznajpowiedzenia.quiz.Question;
import com.example.poznajpowiedzenia.quiz.QuizActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends AppCompatActivity {

    Button btnLogOut;
    Button btnBack;
    Button btnQuiz;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogOut = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        btnQuiz = findViewById(R.id.btnQuiz);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();

            startActivity(new Intent(this, LoginActivity.class));
        });

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnQuiz.setOnClickListener(view -> {
            Intent i = new Intent(this, QuizActivity.class);
            List<Question> questions= getQuestions();
            ListOfQuestions listOfQuestions = new ListOfQuestions(questions, 1, 0);
            i.putExtra("questions", listOfQuestions);
            startActivity(i);
        });
    }

    private List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        List<Proverb> proverbs = AppController.getInstance2();
        //wybieranie 10 powiedzen, sprawdzenie czy odpowiedzi sie nie powtarzaja
        boolean isAnswerReapeted = true;
        while (isAnswerReapeted)
        {
            Collections.shuffle(proverbs);
            isAnswerReapeted = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 1; j < 10; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (proverbs.get(i).getMeaning().equals(proverbs.get(j).getMeaning())) {
                        isAnswerReapeted = true;
                        break;
                    }
                }
            }
        }
        final List<Proverb> proverbsForQuiz = proverbs.subList(0, 10);
        //rozlosowanie odpowiedzi do kazdego powiedzenia
        proverbsForQuiz.forEach(proverb -> { //dla kazdego proverba losujemy odpowiedzi
            List<Proverb> proverbsInQuestion = new ArrayList<>();
            proverbsInQuestion.addAll(proverbsForQuiz);
            proverbsInQuestion.remove(proverb); //mamy liste proverbow bez tego dla ktorego tworzymy odpowiedzi
            Collections.shuffle(proverbsInQuestion);
            List<String> incorrectAnswers = proverbsInQuestion.subList(0, 3).stream().map(Proverb::getMeaning).collect(Collectors.toList());
            questions.add(new Question(proverb.getTitle(), incorrectAnswers, proverb.getMeaning()));
        });
        return questions;
    }

}