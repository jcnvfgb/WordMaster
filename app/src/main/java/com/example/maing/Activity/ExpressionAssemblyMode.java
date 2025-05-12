package com.example.maing.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.R;
import com.example.maing.Utils.ExpressionAssembly;

import java.util.Arrays;
import java.util.List;

public class ExpressionAssemblyMode extends AppCompatActivity implements ExpressionAssembly.AnswerCheckListener {

    //Сборка выражения
    private ExpressionAssembly gameController;

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

        List<String> correctAnswer = Arrays.asList("I", "love", "learning", "languages");

        gameController = new ExpressionAssembly(
                this,
                findViewById(R.id.word_bank),
                findViewById(R.id.answer_area),
                correctAnswer,
                this
        );

        setupCheckButton();
    }

    private void setupCheckButton() {
        TextView checkButton = findViewById(R.id.checkExpression);
        checkButton.setOnClickListener(v -> gameController.checkAnswer());
    }

    @Override
    public void onAnswerChecked(boolean isCorrect) {
        String message = isCorrect ? "Правильно! Молодец!" : "Попробуй еще раз!";
        if (isCorrect) {
            List<String> correctAnswer = Arrays.asList("I", "dont't", "love", "learning", "languages");

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
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}