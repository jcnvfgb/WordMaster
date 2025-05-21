package com.example.maing.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.R;

public class Score extends AppCompatActivity {
    String setName = "";
    int correct = 0, wrong = 0, skipQ = 0, idSet = 0;
    TextView totalQuestion, scoreProcent, wrongAnsw, correctAnsw, skipQuest, finishBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SCOREACTIVITY), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        correct = getIntent().getIntExtra("correct", -1);
        wrong = getIntent().getIntExtra("wrong", -1);
        skipQ = getIntent().getIntExtra("skipQ", -1);
        idSet = getIntent().getIntExtra("idSet", -1);

        if (idSet != -1)
            setName = dbHelper.getSetNameById(idSet);

        totalQuestion = findViewById(R.id.totalQuestion);
        scoreProcent = findViewById(R.id.scoreProcent);
        wrongAnsw = findViewById(R.id.wrongAnsw);
        correctAnsw = findViewById(R.id.correctAnsw);
        skipQuest = findViewById(R.id.skipQuest);
        finishBtn = findViewById(R.id.finishScore);

        String totalQuest = String.valueOf(correct + wrong + skipQ);
        totalQuestion.setText(totalQuest);

        wrongAnsw.setText(String.valueOf(wrong));
        correctAnsw.setText(String.valueOf(correct));
        skipQuest.setText(String.valueOf(skipQ));

        int procent = ((100 * correct) / (correct + wrong + skipQ));
        scoreProcent.setText(String.valueOf(procent));

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.addStatsEntry(
                        setName,
                        correct,
                        wrong,
                        skipQ
                );
                finish();
            }
        });
    }
}