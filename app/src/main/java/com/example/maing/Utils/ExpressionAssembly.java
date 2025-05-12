package com.example.maing.Utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.maing.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpressionAssembly {
    public interface AnswerCheckListener {
        void onAnswerChecked(boolean isCorrect);
    }

    private final FlexboxLayout wordBank;
    private final FlexboxLayout answerArea;
    private final List<String> correctAnswer;
    private final AnswerCheckListener listener;
    private final Context context;

    public ExpressionAssembly(
            @NonNull Context context,
            @NonNull FlexboxLayout wordBank,
            @NonNull FlexboxLayout answerArea,
            @NonNull List<String> correctAnswer,
            @NonNull AnswerCheckListener listener
    ) {
        this.context = context;
        this.wordBank = wordBank;
        this.answerArea = answerArea;
        this.correctAnswer = correctAnswer;
        this.listener = listener;
        initializeGame();
    }

    private void initializeGame() {
        clearContainers();
        List<String> shuffledWords = new ArrayList<>(correctAnswer);
        Collections.shuffle(shuffledWords);

        for (String word : shuffledWords) {
            wordBank.addView(createWordView(word, false));
        }
    }

    private TextView createWordView(String text, boolean isInAnswerArea) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setPadding(dpToPx(16), dpToPx(8), dpToPx(16), dpToPx(8));
        textView.setTextSize(18);

        textView.setBackgroundResource(isInAnswerArea ?
                R.drawable.answer_word_bg :
                R.drawable.word_bank_bg);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));
        textView.setLayoutParams(params);

        textView.setOnClickListener(v -> handleWordClick(textView, isInAnswerArea));
        return textView;
    }

    private void handleWordClick(TextView clickedView, boolean isInAnswerArea) {
        if (isInAnswerArea) {
            moveToBank(clickedView);
        } else {
            moveToAnswer(clickedView);
        }
    }

    private void moveToBank(TextView view) {
        answerArea.removeView(view);
        wordBank.addView(createWordView(view.getText().toString(), false));
    }

    private void moveToAnswer(TextView view) {
        wordBank.removeView(view);
        answerArea.addView(createWordView(view.getText().toString(), true));
    }

    public void checkAnswer() {
        List<String> userAnswer = new ArrayList<>();
        for (int i = 0; i < answerArea.getChildCount(); i++) {
            userAnswer.add(((TextView) answerArea.getChildAt(i)).getText().toString());
        }
        listener.onAnswerChecked(userAnswer.equals(correctAnswer));
    }

    private void clearContainers() {
        wordBank.removeAllViews();
        answerArea.removeAllViews();
    }

    private int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
