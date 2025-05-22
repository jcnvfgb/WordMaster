package com.example.maing.Utils;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.WordModel;
import com.example.maing.Domain.WordPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchingModeController {
    private List<WordPair> generalPairs;
    private List<WordPair> originalPairs;
    private List<WordPair> leftWords;
    private List<WordPair> rightTranslations;
    private int selectedLeftPosition = -1;
    private GameStateListener listener;
    private int loopGame = 0;

    public interface GameStateListener {
        void onSelectionChanged(int leftPosition);
        void onMatchSuccess(int leftPosition, int rightPosition);
        void onMatchError(String message);
        void onGameCompleted();
        void onGameReset();
        void onNextLevel();
        void onOverGame();
    }

    public MatchingModeController(GameStateListener listener, ArrayList<WordModel> questionItems) {
        this.listener = listener;
        generalPairs = new ArrayList<>();
        /*generalPairs = Arrays.asList(
                new WordPair("Hello", "Привет"),
                new WordPair("Goodbye", "Пока"),
                new WordPair("Cat", "Кошка"),
                new WordPair("Dog", "Собака"),
                new WordPair("Book", "Книга"),
                new WordPair("Tree", "Дерево"),
                new WordPair("Car", "Машина"),
                new WordPair("Error", "Ошибка"),
                new WordPair("Failure", "Отказ"),
                new WordPair("Bag", "Баг"),
                new WordPair("Damn", "Черт"),
                new WordPair("Love", "Любить"),
                new WordPair("Hate", "Ненавидеть"),
                new WordPair("Android", "Андройд")
        );*/
        for(WordModel item : questionItems) {
            generalPairs.add(new WordPair(item.getWord(), item.getTranslation()));
        }
        initializeData();
    }

    private void initializeData() {
        int fromIndex = loopGame * 5;
        int toIndex = Math.min(fromIndex + 5, generalPairs.size());

        if(fromIndex >= generalPairs.size()) {
            return;
        }

        originalPairs = new ArrayList<>(generalPairs.subList(fromIndex, toIndex));
        leftWords = new ArrayList<>(originalPairs);

        List<WordPair> shuffled = new ArrayList<>(originalPairs);
        Collections.shuffle(shuffled);
        rightTranslations = new ArrayList<>(shuffled);
    }

    public void selectLeftWord(int position) {
        if (position < 0 || position >= leftWords.size()) return;

        if (selectedLeftPosition != -1) {
            leftWords.get(selectedLeftPosition).setSelected(false);
            listener.onSelectionChanged(selectedLeftPosition);
        }

        selectedLeftPosition = position;
        leftWords.get(position).setSelected(true);
        listener.onSelectionChanged(position);
    }

    public void checkRightAnswer(int rightPosition) {
        if (selectedLeftPosition == -1) {
            listener.onMatchError("Сначала выберите слово слева");
            return;
        }

        WordPair leftPair = leftWords.get(selectedLeftPosition);
        WordPair rightPair = rightTranslations.get(rightPosition);

        if (originalPairs.indexOf(leftPair) == originalPairs.indexOf(rightPair)) {
            leftPair.setMatched(true);
            rightPair.setMatched(true);
            listener.onMatchSuccess(selectedLeftPosition, rightPosition);
            checkGameCompletion();
        } else {
            listener.onMatchError("Неверное сопоставление");
        }

        leftPair.setSelected(false);
        listener.onSelectionChanged(selectedLeftPosition);
        selectedLeftPosition = -1;
    }

    private void checkGameCompletion() {
        boolean isOver = false;
        for (WordPair pair : originalPairs) {
            if (!pair.isMatched()) return;
        }
        for (WordPair pair : generalPairs) {
            if (!pair.isMatched()) return;
            isOver = true;
        }
        if (isOver == true) {
            listener.onOverGame();
        } else {
            listener.onGameCompleted();
        }
    }

    public void resetGame() {
        // Сбрасываем все флаги и состояния
        for (WordPair pair : generalPairs) {
            pair.setMatched(false);
            pair.setSelected(false);
        }
        loopGame = 0;
        int fromIndex = loopGame * 5;
        int toIndex = Math.min(fromIndex + 5, generalPairs.size());

        if(fromIndex >= generalPairs.size()) {
            return;
        }

        originalPairs = new ArrayList<>(generalPairs.subList(fromIndex, toIndex));

        // Восстанавливаем исходный порядок слева
        leftWords.clear();
        leftWords.addAll(originalPairs);

        // Перемешиваем правый список
        List<WordPair> shuffled = new ArrayList<>(originalPairs);
        Collections.shuffle(shuffled);
        rightTranslations.clear();
        rightTranslations.addAll(shuffled);

        // Сбрасываем выбор
        selectedLeftPosition = -1;

        // Уведомляем слушателя
        listener.onGameReset();
    }

    public void nextLoop() {
        for (WordPair pair : originalPairs) {
            if (!pair.isMatched()) return;
        }
        loopGame++;
        int fromIndex = loopGame * 5;
        int toIndex = Math.min(fromIndex + 5, generalPairs.size());

        if(fromIndex >= generalPairs.size()) {
            return;
        }

        originalPairs = new ArrayList<>(generalPairs.subList(fromIndex, toIndex));

        // Восстанавливаем исходный порядок слева
        leftWords.clear();
        leftWords.addAll(originalPairs);

        // Перемешиваем правый список
        List<WordPair> shuffled = new ArrayList<>(originalPairs);
        Collections.shuffle(shuffled);
        rightTranslations.clear();
        rightTranslations.addAll(shuffled);

        // Сбрасываем выбор
        selectedLeftPosition = -1;

        // Уведомляем слушателя
        listener.onNextLevel();
    }

    public void skipLoop() {
        for (WordPair pair : originalPairs) {
            pair.setMatched(true);
        }
        loopGame++;
        int fromIndex = loopGame * 5;
        int toIndex = Math.min(fromIndex + 5, generalPairs.size());

        if(fromIndex >= generalPairs.size()) {
            return;
        }

        originalPairs = new ArrayList<>(generalPairs.subList(fromIndex, toIndex));

        // Восстанавливаем исходный порядок слева
        leftWords.clear();
        leftWords.addAll(originalPairs);

        // Перемешиваем правый список
        List<WordPair> shuffled = new ArrayList<>(originalPairs);
        Collections.shuffle(shuffled);
        rightTranslations.clear();
        rightTranslations.addAll(shuffled);

        // Сбрасываем выбор
        selectedLeftPosition = -1;

        // Уведомляем слушателя
        listener.onNextLevel();
    }

    public List<WordPair> getLeftWords() { return leftWords; }
    public List<WordPair> getRightTranslations() { return rightTranslations; }
}