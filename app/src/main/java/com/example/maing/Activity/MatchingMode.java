package com.example.maing.Activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Adapter.LeftWordAdapter;
import com.example.maing.Adapter.RightTranslationAdapter;
import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;
import com.example.maing.Utils.MatchingModeController;

import java.util.ArrayList;

public class MatchingMode extends AppCompatActivity implements MatchingModeController.GameStateListener {
    TextView skipQuestion;
    private RecyclerView rvLeft, rvRight;
    private LeftWordAdapter leftAdapter;
    private RightTranslationAdapter rightAdapter;
    private MatchingModeController matchingModeController;
    DatabaseHelper databaseHelper;
    ArrayList<WordModel> questionItems;
    int correct = 0, wrong = 0, skipQ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_matching_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MATCHINGMODE), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        int id_set = getIntent().getIntExtra("idSet", 0);

        questionItems = databaseHelper.getWordsBySetId(id_set);

        matchingModeController = new MatchingModeController(this, questionItems);
        setupRecyclerViews();

        skipQuestion = findViewById(R.id.skipQuestion);
        skipQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipQ++;
                matchingModeController.nextLoop();
            }
        });
    }

    private void setupRecyclerViews() {
        rvLeft = findViewById(R.id.rvLeftWords);
        rvRight = findViewById(R.id.rvRightTranslations);

        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        rvRight.setLayoutManager(new LinearLayoutManager(this));

        leftAdapter = new LeftWordAdapter(matchingModeController.getLeftWords(), position ->
                matchingModeController.selectLeftWord(position)
        );

        rightAdapter = new RightTranslationAdapter(matchingModeController.getRightTranslations(), position ->
                matchingModeController.checkRightAnswer(position)
        );

        rvLeft.setAdapter(leftAdapter);
        rvRight.setAdapter(rightAdapter);
    }

    // Реализация GameStateListener
    @Override
    public void onSelectionChanged(int leftPosition) {
        leftAdapter.notifyItemChanged(leftPosition);
    }

    @Override
    public void onMatchSuccess(int leftPosition, int rightPosition) {
        leftAdapter.notifyItemChanged(leftPosition);
        rightAdapter.notifyItemChanged(rightPosition);
        correct++;
    }

    @Override
    public void onMatchError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        wrong++;
    }

    @Override
    public void onGameCompleted() {
        new AlertDialog.Builder(this)
                .setTitle("Поздравляем!")
                .setMessage("Все слова сопоставлены правильно!")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onGameReset() {
        // Обновляем оба адаптера
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();

        // Можно добавить анимацию или другие эффекты
        Toast.makeText(this, "Игра сброшена!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextLevel() {
        // Обновляем оба адаптера
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();

        // Можно добавить анимацию или другие эффекты
        Toast.makeText(this, "Следующий уровень!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOverGame() {
        // Обновляем оба адаптера
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();

        // Можно добавить анимацию или другие эффекты
        Toast.makeText(this, "It's over! No questions available!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MatchingMode.this, Score.class);
        intent.putExtra("correct", correct);
        intent.putExtra("wrong", wrong);
        intent.putExtra("skipQ", skipQ);
        startActivity(intent);
        finish();
    }

    // Добавляем кнопку сброса в меню или layout
    public void onResetButtonClick(View view) {
        matchingModeController.resetGame();
    }

    // Добавляем следующего уровня
    public void onNextLevelButtonClick(View view) {
        matchingModeController.nextLoop();
    }
}