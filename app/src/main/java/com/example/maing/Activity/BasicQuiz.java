package com.example.maing.Activity;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BasicQuiz extends AppCompatActivity {

    TextView quizText, answer1, answer2, answer3, answer4, skip;
    ArrayList<WordModel> questionItems;
    LinearLayout activityButtons;
    TextView btnBad, btnMiddle, btnGood, btnGreat;
    int currentQuestion = 0;
    int correct = 0, wrong = 0, skipQ = 0;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TRAINING), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        quizText = findViewById(R.id.quizText);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        skip = findViewById(R.id.skipQuestionBQ);

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

        int freeMode = getIntent().getIntExtra("freeMode", 0);
        int id_set = getIntent().getIntExtra("idSet", 0);
        if (freeMode == 1) {
            if (id_set == 0) {
                finish();
            } else {
                questionItems = databaseHelper.getWordsBySetId(id_set);
            }
        } else {
            questionItems = databaseHelper.getBadAndMiddleWords();
        }

        if (questionItems == null || questionItems.isEmpty()) {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
            finish();
            return; // Прерываем выполнение
        }

        Log.d("BasicQuiz", "Size of questionItems: " + questionItems.size());

        setQuestionScreen(currentQuestion);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionItems.get(currentQuestion).getTranslation().equals(
                        answer1.getText().toString().trim()
                )) {
                    Log.d("BasicQuiz", "The answer: cockerect");
                    correct++;
                    answer1.setBackgroundResource(R.color.green);
                } else {
                    Log.d("BasicQuiz", "The answer: not cockerect");
                    wrong++;
                    answer1.setBackgroundResource(R.color.red);
                }

                skip.setVisibility(View.GONE);
                activityButtons.setVisibility(View.VISIBLE);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionItems.get(currentQuestion).getTranslation().equals(
                        answer2.getText().toString().trim()
                )) {
                    Log.d("BasicQuiz", "The answer: cockerect");
                    correct++;
                    answer2.setBackgroundResource(R.color.green);
                } else {
                    Log.d("BasicQuiz", "The answer: not cockerect");
                    wrong++;
                    answer2.setBackgroundResource(R.color.red);
                }

                skip.setVisibility(View.GONE);
                activityButtons.setVisibility(View.VISIBLE);
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionItems.get(currentQuestion).getTranslation().equals(
                        answer3.getText().toString().trim()
                )) {
                    Log.d("BasicQuiz", "The answer: cockerect");
                    correct++;
                    answer3.setBackgroundResource(R.color.green);
                } else {
                    Log.d("BasicQuiz", "The answer: not cockerect");
                    wrong++;
                    answer3.setBackgroundResource(R.color.red);
                }

                skip.setVisibility(View.GONE);
                activityButtons.setVisibility(View.VISIBLE);
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionItems.get(currentQuestion).getTranslation().equals(
                        answer4.getText().toString().trim()
                )) {
                    Log.d("BasicQuiz", "The answer: cockerect");
                    correct++;
                    answer4.setBackgroundResource(R.color.green);
                } else {
                    Log.d("BasicQuiz", "The answer: not cockerect");
                    wrong++;
                    answer4.setBackgroundResource(R.color.red);
                }

                skip.setVisibility(View.GONE);
                activityButtons.setVisibility(View.VISIBLE);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipQ++;
                handleActivitySelection("bad");
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
        skip.setVisibility(View.VISIBLE);

        if(currentQuestion < questionItems.size() - 1) {
            currentQuestion++;
            setQuestionScreen(currentQuestion);
        } else {
            Intent intent = new Intent(BasicQuiz.this, Score.class);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            intent.putExtra("skipQ", skipQ);
            startActivity(intent);
            finish();
        }
    }

    private void setQuestionScreen(int currentQuest) {
        answer1.setBackgroundResource(R.color.base_color_answer);
        answer2.setBackgroundResource(R.color.base_color_answer);
        answer3.setBackgroundResource(R.color.base_color_answer);
        answer4.setBackgroundResource(R.color.base_color_answer);

        /*quizText.setText(questionItems.get(currentQuest).getWord());

        databaseHelper = new DatabaseHelper(this);
        ArrayList<String> answerOptions = new ArrayList<>();
        answerOptions = databaseHelper.getAllTranslations();

        int randomValue = new Random().nextInt(4) + 1;

        switch (randomValue) {
            case 1:
                answer1.setText(questionItems.get(currentQuest).getTranslation());
                answer2.setText(questionItems.get(currentQuest).getWordActivity());
                answer3.setText(questionItems.get(currentQuest).getWordActivity());
                answer4.setText(questionItems.get(currentQuest).getWordActivity());
                break;
            case 2:
                answer1.setText(questionItems.get(currentQuest).getWordActivity());
                answer2.setText(questionItems.get(currentQuest).getTranslation());
                answer3.setText(questionItems.get(currentQuest).getWordActivity());
                answer4.setText(questionItems.get(currentQuest).getWordActivity());
                break;
            case 3:
                answer1.setText(questionItems.get(currentQuest).getWordActivity());
                answer2.setText(questionItems.get(currentQuest).getWordActivity());
                answer3.setText(questionItems.get(currentQuest).getTranslation());
                answer4.setText(questionItems.get(currentQuest).getWordActivity());
                break;
            case 4:
                answer1.setText(questionItems.get(currentQuest).getWordActivity());
                answer2.setText(questionItems.get(currentQuest).getWordActivity());
                answer3.setText(questionItems.get(currentQuest).getWordActivity());
                answer4.setText(questionItems.get(currentQuest).getTranslation());
                break;
            default:
                answer1.setText(questionItems.get(currentQuest).getTranslation());
                answer2.setText(questionItems.get(currentQuest).getWordActivity());
                answer3.setText(questionItems.get(currentQuest).getTranslation());
                answer4.setText(questionItems.get(currentQuest).getWordActivity());
                break;
        }*/

        // Установка текущего слова
        WordModel currentWord = questionItems.get(currentQuest);
        quizText.setText(currentWord.getWord());

        // Получение всех возможных переводов
        ArrayList<String> answerOptions = databaseHelper.getAllTranslations();

        // Подготовка списка для ответов
        ArrayList<String> answers = new ArrayList<>(4);

        // Правильный ответ
        String correctAnswer = currentWord.getTranslation();

        // Генерация позиции для правильного ответа (1-4)
        int correctPosition = new Random().nextInt(4) + 1;

        // Добавляем правильный ответ
        answers.add(correctAnswer);

        // Фильтруем варианты (исключаем правильный ответ)
        List<String> wrongOptions = answerOptions.stream()
                .filter(s -> !s.equals(correctAnswer))
                .collect(Collectors.toList());

        // Заполняем оставшиеся места случайными уникальными ответами
        for (int i = 1; i < 4; i++) {
            if (wrongOptions.size() > 0) {
                String randomAnswer = wrongOptions.remove(new Random().nextInt(wrongOptions.size()));
                answers.add(randomAnswer);
            } else {
                // Если вариантов не хватает - используем активность слова
                answers.add("---");
            }
        }

        // Перемешиваем ответы
        Collections.shuffle(answers);

        // Устанавливаем ответы в TextView
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
    }
}