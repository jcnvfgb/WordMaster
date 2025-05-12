package com.example.maing.Utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHighlighter {
    private final TextView textView;
    private final String originalText;
    private final Map<String, Integer> wordColors = new HashMap<>();
    private final Map<String, String> wordTooltips = new HashMap<>();
    private final List<WordPosition> wordPositions = new ArrayList<>();

    public TextHighlighter(TextView textView) {
        this.textView = textView;
        this.originalText = textView.getText().toString();
    }

    public TextHighlighter addWord(String word, int color, String tooltip) {
        wordColors.put(word, color);
        wordTooltips.put(word, tooltip);
        return this;
    }

    public void apply() {
        findWordPositions();
        String modifiedText = removeStars();
        applySpans(modifiedText);
    }

    // Найти все вхождения **word** и сохранить их позиции
    private void findWordPositions() {
        wordPositions.clear();
        for (String word : wordColors.keySet()) {
            Pattern pattern = Pattern.compile("\\*\\*" + Pattern.quote(word) + "\\*\\*");
            Matcher matcher = pattern.matcher(originalText);

            while (matcher.find()) {
                int matchStart = matcher.start();
                int matchEnd = matcher.end();
                wordPositions.add(new WordPosition(word, matchStart, matchEnd));
            }
        }

        // Сортируем в обратном порядке (от конца к началу текста)
        Collections.sort(wordPositions, new Comparator<WordPosition>() {
            @Override
            public int compare(WordPosition wp1, WordPosition wp2) {
                return Integer.compare(wp2.matchStart, wp1.matchStart);
            }
        });
    }

    // Удалить ** из текста, начиная с конца
    private String removeStars() {
        StringBuilder sb = new StringBuilder(originalText);

        for (WordPosition wp : wordPositions) {
            // Удаляем ** после слова (сначала правые, потом левые)
            sb.delete(wp.matchEnd - 2, wp.matchEnd);
            sb.delete(wp.matchStart, wp.matchStart + 2);
        }

        return sb.toString();
    }

    // Применить стили к модифицированному тексту
    private void applySpans(String modifiedText) {
        SpannableString spannableString = new SpannableString(modifiedText);
        int offset = 0;

        // Теперь обрабатываем вхождения в исходном порядке (для правильных позиций)
        Collections.sort(wordPositions, new Comparator<WordPosition>() {
            @Override
            public int compare(WordPosition wp1, WordPosition wp2) {
                return Integer.compare(wp1.matchStart, wp2.matchStart);
            }
        });

        for (WordPosition wp : wordPositions) {
            // Новая позиция слова после удаления **
            int newStart = wp.matchStart - offset;
            int newEnd = wp.matchEnd - offset - 4; // -4 из-за удаленных **

            // Применяем цвет
            spannableString.setSpan(
                    new ForegroundColorSpan(wordColors.get(wp.word)),
                    newStart,
                    newEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            // Кликабельность
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), wordTooltips.get(wp.word), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };

            spannableString.setSpan(
                    clickableSpan,
                    newStart,
                    newEnd,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );

            offset += 4; // Учитываем удаленные **
        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static class WordPosition {
        String word;
        int matchStart;
        int matchEnd;

        WordPosition(String word, int matchStart, int matchEnd) {
            this.word = word;
            this.matchStart = matchStart;
            this.matchEnd = matchEnd;
        }
    }
}
