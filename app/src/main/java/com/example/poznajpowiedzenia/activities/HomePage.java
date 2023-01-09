package com.example.poznajpowiedzenia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.quiz.QuizActivity;
import com.google.firebase.auth.FirebaseAuth;

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
            startActivity(new Intent(this, QuizActivity.class));
        });
    }
}