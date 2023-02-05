package com.example.poznajpowiedzenia.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.activities.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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

        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(this, HomePage.class));
        });

        ansA.setOnClickListener( view -> {
            checkAnswer(model, ansA.getText().toString(), ansA);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    startActivity(nextQuestion(model, ansA.getText().toString()));
                }
            }, 1000);
        });

        ansB.setOnClickListener( view -> {
            checkAnswer(model, ansB.getText().toString(), ansB);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    startActivity(nextQuestion(model, ansB.getText().toString()));
                }
            }, 1000);
        });

        ansC.setOnClickListener( view -> {
            checkAnswer(model, ansC.getText().toString(), ansC);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    startActivity(nextQuestion(model, ansC.getText().toString()));
                }
            }, 1000);
        });

        ansD.setOnClickListener( view -> {
            checkAnswer(model, ansD.getText().toString(), ansD);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    startActivity(nextQuestion(model, ansD.getText().toString()));
                }
            }, 1000);
        });
    }

//   startActivity(nextQuestion(model, model.questionList().get(model.getNumberOfQuestion() - 1).getCorrect()));

    public Intent nextQuestion(ListOfQuestions model, String answer) {
        Intent i;
        if (model.questionList().get(model.getNumberOfQuestion() - 1).getCorrect().equals(answer)) {
            model.increaseNumberOfCorrectAnswers();
        }
        if (model.getNumberOfQuestion() == 10) {
            i = new Intent(this, HomePage.class);
            i.putExtra("LastQuiz", model.getNumberOfCorrectAnswers());

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String email = mAuth.getCurrentUser().getEmail();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> result = new HashMap<>();
            result.put("result", model.getNumberOfCorrectAnswers());

            db.collection("Users")
                    .document(email)
                    .set(result);

            return i;
        } else {
            model.increaseNumberOfQuestion();

            i = new Intent(this, QuizActivity.class);
            i.putExtra("questions", model);
        }
        return i;
    }

    public void checkAnswer(ListOfQuestions model, String answer, TextView button) {
        if (model.questionList().get(model.getNumberOfQuestion() - 1).getCorrect().equals(answer)) {
            button.setBackgroundColor(Color.parseColor("#7ED957"));
        } else {
            button.setBackgroundColor(Color.parseColor("#FF1616"));
        }
    }
}