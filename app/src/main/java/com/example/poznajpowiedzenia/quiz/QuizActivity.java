package com.example.poznajpowiedzenia.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.widget.Button;
import android.widget.TextView;

import com.example.poznajpowiedzenia.R;
import com.example.poznajpowiedzenia.activities.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    Button btn_back;
    TextView question;
    TextView ansA;
    TextView ansB;
    TextView ansC;
    TextView ansD;
    TextView numberOfQuestion;
    Button btn_speak;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pl"));
                }
            }
        });

        btn_back = findViewById(R.id.back_btn);
        btn_speak = findViewById(R.id.speakBtn);
        question = findViewById(R.id.question);
        numberOfQuestion = findViewById(R.id.number_of_question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);

        ListOfQuestions model = (ListOfQuestions) getIntent().getSerializableExtra("questions");

        numberOfQuestion.setText("Numer pytania: "+model.getNumberOfQuestion());

        question.setText(model.questionList().get(model.getNumberOfQuestion() - 1).getTitle());
        List<String> answerList = new ArrayList<>();
        answerList.addAll(model.questionList().get(model.getNumberOfQuestion() - 1).getIncorrect());
        answerList.add(model.questionList().get(model.getNumberOfQuestion() - 1).getCorrect());
        Collections.shuffle(answerList);//przelosowanie wszystkich odpowiedzi
        ansA.setText(answerList.get(0));
        ansB.setText(answerList.get(1));
        ansC.setText(answerList.get(2));
        ansD.setText(answerList.get(3));

        question = findViewById(R.id.question);

        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(this, HomePage.class));
        });

        btn_speak.setOnClickListener(view -> {
                textToSpeech.speak("Wybierz poprawne wyjaśnienie przysłowia "
                                + question.getText().toString(),
                        TextToSpeech.QUEUE_ADD,
                        null);
                textToSpeech.speak(" Odpowiedź A " + ansA.getText().toString(),
                        TextToSpeech.QUEUE_ADD,
                        null);
                textToSpeech.speak(" Odpowiedź B " + ansB.getText().toString(),
                        TextToSpeech.QUEUE_ADD,
                        null);
                textToSpeech.speak(" Odpowiedź C " + ansC.getText().toString(),
                        TextToSpeech.QUEUE_ADD,
                        null);
                textToSpeech.speak(" Odpowiedź D " + ansD.getText().toString(),
                        TextToSpeech.QUEUE_ADD,
                        null);
        });

        ansA.setOnClickListener( view -> {
            blockButtonsAfterAnswer();
            checkAnswer(model, ansA.getText().toString(), ansA);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 1s = 1000ms
                    startActivity(nextQuestion(model, ansA.getText().toString()));
                }
            }, 1000);
            textToSpeech.stop();
        });

        ansB.setOnClickListener( view -> {
            blockButtonsAfterAnswer();
            checkAnswer(model, ansB.getText().toString(), ansB);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 1s = 1000ms
                    startActivity(nextQuestion(model, ansB.getText().toString()));
                }
            }, 1000);
            textToSpeech.stop();
        });

        ansC.setOnClickListener( view -> {
            blockButtonsAfterAnswer();
            checkAnswer(model, ansC.getText().toString(), ansC);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 1s = 1000ms
                    startActivity(nextQuestion(model, ansC.getText().toString()));
                }
            }, 1000);
            textToSpeech.stop();
        });

        ansD.setOnClickListener( view -> {
            blockButtonsAfterAnswer();
            checkAnswer(model, ansD.getText().toString(), ansD);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 1s = 1000ms
                    startActivity(nextQuestion(model, ansD.getText().toString()));
                }
            }, 1000);
            textToSpeech.stop();
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

    public void blockButtonsAfterAnswer() {
        ansA.setEnabled(false);
        ansB.setEnabled(false);
        ansC.setEnabled(false);
        ansD.setEnabled(false);
    }
}