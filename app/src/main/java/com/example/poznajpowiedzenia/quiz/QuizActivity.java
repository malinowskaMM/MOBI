package com.example.poznajpowiedzenia.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.activities.HomePage;

public class QuizActivity extends AppCompatActivity {

    Button btn_back;
    TextView question;
    TextView ansA;
    TextView ansB;
    TextView ansC;
    TextView ansD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        btn_back = findViewById(R.id.back_btn);

        question = findViewById(R.id.question);

        btn_back.setOnClickListener( view -> {
            startActivity(new Intent(this, HomePage.class));
        });
    }
}