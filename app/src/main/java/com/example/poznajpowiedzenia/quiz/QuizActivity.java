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
    TextView numberOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        btn_back = findViewById(R.id.back_btn);
        question = findViewById(R.id.question);
//        numberOfQuestion = findViewById(R.id.number_of_question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        ListOfQuestions model = (ListOfQuestions) getIntent().getSerializableExtra("questions");
//        numberOfQuestion.setText(model.getNumberOfQuestion());
        question.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getTitle());
        ansA.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getCorrect());
        ansB.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getIncorrect().get(0));
        ansC.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getIncorrect().get(1));
        ansD.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getIncorrect().get(2));

        question = findViewById(R.id.question);

        btn_back.setOnClickListener( view -> {
            startActivity(new Intent(this, HomePage.class));
        });
    }
}