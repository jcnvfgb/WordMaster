package com.example.maing.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhraseSplitter {

    public List<String> splitPhrase(String inputPhrase) {
        // Удаляем лишние пробелы в начале и конце строки
        String trimmedPhrase = inputPhrase.trim();

        // Разбиваем фразу на слова, используя пробел как разделитель
        // Регулярное выражение "\\s+" означает "один или более пробелов"
        String[] wordsArray = trimmedPhrase.split("\\s+");

        // Преобразуем массив в List и возвращаем результат
        return new ArrayList<>(Arrays.asList(wordsArray));
    }
}
