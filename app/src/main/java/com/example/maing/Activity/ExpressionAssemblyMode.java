package com.example.maing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.maing.Utils.ExpressionAssembly;
import com.example.maing.Utils.PhraseSplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpressionAssemblyMode extends AppCompatActivity implements ExpressionAssembly.AnswerCheckListener {

    //Сборка выражения
    private ExpressionAssembly gameController;
    DatabaseHelper databaseHelper;
    ArrayList<WordModel> questionItems;
    int currentQuestion = 0;
    int correct = 0, wrong = 0, skipQ = 0;
    TextView skipQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expression_assembly_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ExpressionAssemblyMode), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        skipQuestion = findViewById(R.id.skipQuestion);

        PhraseSplitter phraseSplitter = new PhraseSplitter();

        databaseHelper = new DatabaseHelper(this);

        int id_set = getIntent().getIntExtra("idSet", 0);

        questionItems = databaseHelper.getWordsBySetId(id_set);

        List<String> correctAnswer = phraseSplitter.splitPhrase(questionItems.get(currentQuestion).getWord());

        gameController = new ExpressionAssembly(
                this,
                findViewById(R.id.word_bank),
                findViewById(R.id.answer_area),
                correctAnswer,
                this
        );

        setupCheckButton();

        skipQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipQ++;
                setSkipQuestion();
            }
        });
    }

    private void setupCheckButton() {
        TextView checkButton = findViewById(R.id.checkExpression);
        checkButton.setOnClickListener(v -> gameController.checkAnswer());
    }

    private void setSkipQuestion() {
        TextView checkButton = findViewById(R.id.checkExpression);
        checkButton.setOnClickListener(v -> gameController.checkAnswer());
        PhraseSplitter phraseSplitter = new PhraseSplitter();
        List<String> correctAnswer = phraseSplitter.splitPhrase("I love learning languages");;
        if(currentQuestion < questionItems.size() - 1) {
            currentQuestion++;
            correctAnswer = phraseSplitter.splitPhrase(questionItems.get(currentQuestion).getWord());
        } else {
            int id_set = getIntent().getIntExtra("idSet", 0);
            Intent intent = new Intent(ExpressionAssemblyMode.this, Score.class);
            intent.putExtra("correct", correct);
            intent.putExtra("wrong", wrong);
            intent.putExtra("skipQ", skipQ);
            intent.putExtra("idSet", id_set);
            startActivity(intent);
            finish();
        }

        gameController = new ExpressionAssembly(
                this,
                findViewById(R.id.word_bank),
                findViewById(R.id.answer_area),
                correctAnswer,
                this
        );

        setupCheckButton();
    }

    @Override
    public void onAnswerChecked(boolean isCorrect) {
        String message = isCorrect ? "Правильно! Молодец!" : "Попробуй еще раз!";
        if (isCorrect) {
            correct++;
            PhraseSplitter phraseSplitter = new PhraseSplitter();
            List<String> correctAnswer = phraseSplitter.splitPhrase("I love learning languages");;
            if(currentQuestion < questionItems.size() - 1) {
                currentQuestion++;
                correctAnswer = phraseSplitter.splitPhrase(questionItems.get(currentQuestion).getWord());
            } else {
                int id_set = getIntent().getIntExtra("idSet", 0);
                Intent intent = new Intent(ExpressionAssemblyMode.this, Score.class);
                intent.putExtra("correct", correct);
                intent.putExtra("wrong", wrong);
                intent.putExtra("skipQ", skipQ);
                intent.putExtra("idSet", id_set);
                startActivity(intent);
                finish();
            }

            gameController = new ExpressionAssembly(
                    this,
                    findViewById(R.id.word_bank),
                    findViewById(R.id.answer_area),
                    correctAnswer,
                    this
            );

            setupCheckButton();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            wrong++;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}