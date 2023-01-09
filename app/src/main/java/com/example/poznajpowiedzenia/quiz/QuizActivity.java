package com.example.poznajpowiedzenia.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.activities.HomePage;

public class QuizActivity extends AppCompatActivity {

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btn_back = findViewById(R.id.back_btn);

        btn_back.setOnClickListener( view -> {
            startActivity(new Intent(this, HomePage.class));
        });
    }
}