package com.example.poznajpowiedzenia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.controller.AppController;
import com.example.poznajpowiedzenia.data.wiki.Proverb;
import com.example.poznajpowiedzenia.quiz.Question;
import com.example.poznajpowiedzenia.quiz.QuizActivity;
import com.example.poznajpowiedzenia.quote.QuoteFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            i.putExtra("key", "value");
            startActivity(i);
        });
    }

    private List<Question> getProverbs() {
        List<Question> questions = new ArrayList<>();
        List<Proverb> proverbs = AppController.getInstance2();
        //wybieranie 10 powiedzen
        Collections.shuffle(proverbs);
        final List<Proverb> proverbsForQuiz = proverbs.subList(0, 9);
        //rozlosowanie odpowiedzi do kazdego powiedzenia
        proverbsForQuiz.forEach(proverb -> {
            List<Proverb> proverbsInQuestion = proverbsForQuiz;
            proverbsInQuestion.remove(proverb);
            //questions.add(new Question(proverb.getTitle(), proverb.getMeaning()))
        });
        //return proverbsForQuiz;
        return null;
    }

}