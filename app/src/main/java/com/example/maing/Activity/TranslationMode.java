package com.example.maing.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

import java.util.ArrayList;

public class TranslationMode extends AppCompatActivity {
    TextView quizText, checkAnswer;
    EditText answerField;
    ArrayList<WordModel> questionItems;
    LinearLayout activityButtons;
    TextView btnBad, btnMiddle, btnGood, btnGreat;
    int currentQuestion = 0;
    int correct = 0, wrong = 0;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_translation_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TRANSLATIONMODE), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        quizText = findViewById(R.id.wordForTranslation);
        checkAnswer = findViewById(R.id.checkingTheAnswer);
        answerField = findViewById(R.id.answerField);

        int id_set = getIntent().getIntExtra("idSet", 0);
        questionItems = databaseHelper.getWordsBySetId(id_set);

        activityButtons = findViewById(R.id.activity_buttons);
        btnBad = findViewById(R.id.btn_bad);
        btnMiddle = findViewById(R.id.btn_middle);
        btnGood = findViewById(R.id.btn_good);
        btnGreat = findViewById(R.id.btn_great);

        // Обработчики для кнопок активности
        btnBad.setOnClickListener(v -> handleActivitySelection("bad"));
        btnMiddle.setOnClickListener(v -> handleActivitySelection("middle"));
        btnGood.setOnClickListener(v -> handleActivitySelection("good"));
        btnGreat.setOnClickListener(v -> handleActivitySelection("great"));

        if (questionItems == null || questionItems.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
            return; // Прерываем выполнение
        }

        setQuestionScreen(currentQuestion);

        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionItems.get(currentQuestion).getTranslation().equals(
                        answerField.getText().toString().trim()
                )) {
                    Log.d("BasicQuiz", "The answer: cockerect");
                    correct++;
                    answerField.setBackgroundResource(R.color.green);
                } else {
                    Log.d("BasicQuiz", "The answer: not cockerect");
                    wrong++;
                    answerField.setBackgroundResource(R.color.red);
                }

                activityButtons.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleActivitySelection(String activity) {
        // Обновление активности в БД
        WordModel currentWord = questionItems.get(currentQuestion);
        databaseHelper.updateWord(
                currentWord.getId_word(),
                currentWord.getWord(),
                currentWord.getTranslation(),
                currentWord.getId_set(),
                activity
        );

        // Переход к следующему вопросу
        activityButtons.setVisibility(View.GONE);

        if(currentQuestion < questionItems.size() - 1) {
            currentQuestion++;
            setQuestionScreen(currentQuestion);
        } else {
            finish();
        }
    }

    private void setQuestionScreen(int currentQuest) {
        answerField.setBackgroundResource(R.drawable.edit_shape);
        answerField.setText("");
        WordModel currentWord = questionItems.get(currentQuest);
        quizText.setText(currentWord.getWord());
    }
}