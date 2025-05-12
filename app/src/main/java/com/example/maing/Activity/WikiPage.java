package com.example.maing.Activity;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.R;
import com.example.maing.Utils.TextHighlighter;

public class WikiPage extends AppCompatActivity {
    TextView namePage, mainText;

    String text1 = "Hiragana is the basic Japanese phonetic script. It represents every sound in the" +
            " Japanese language. Therefore, you can theoretically write everything in Hiragana. " +
            "However, because Japanese is written with no spaces, this will create nearly " +
            "indecipherable text.\n" +
            "\n" +
            "Here is a table of Hiragana and similar-sounding English consonant-vowel pronunciations. " +
            "It is read up to down and right to left, which is how most Japanese books are written. " +
            "In Japanese, writing the strokes in the correct order and direction is important, " +
            "especially for Kanji. Because handwritten letters look slightly different from typed letters " +
            "(just like how 'a' looks totally different when typed), you will want to use a resource " +
            "that uses handwritten style fonts to show you how to write the characters (see below " +
            "for links). I must also stress the importance of correctly learning how to pronounce " +
            "each sound. Since every word in Japanese is composed of these sounds, learning an " +
            "incorrect pronunciation for a letter can severely damage the very foundation on " +
            "which your pronunciation lies.\n";

    String text2 = "You can listen to the pronunciation for each character by clicking on it in chart. " +
            "If your browser doesn't support audio, you can also download them at " +
            "http://www.guidetojapanese.org/audio/basic_sounds.zip. There are also other free resources " +
            "with audio samples.\n" +
            "\n" +
            "**Hiragana** is not too tough to master or teach and as a result, there are a variety of " +
            "web sites and free programs that are already available on the web. I also suggest " +
            "recording yourself and comparing the sounds to make sure you're getting it right.\n" +
            "\n" +
            "When practicing writing Hiragana by hand, the important thing to remember is that the " +
            "stroke order and direction of the strokes matter. Trust me, you'll eventually find out " +
            "why when you read other people's hasty notes that are nothing more than chicken scrawls. " +
            "The only thing that will help you is that everybody writes in the same order and so the " +
            "flow of the characters is fairly consistent. I strongly recommend that you pay close " +
            "attention to stroke order from the beginning starting with Hiragana to avoid falling " +
            "into bad habits. While there are many tools online that aim to help you learn Hiragana, " +
            "the best way to learn how to write it is the old fashioned way: a piece of paper and " +
            "pen/pencil. Below are handy PDFs for Hiragana writing practice.\n";

    String text3 = "As an aside, an old Japanese poem called **「いろは」** was often used as the base for " +
            "ordering of Hiragana until recent times. The poem contains every single Hiragana character " +
            "except for 「ん」 which probably did not exist at the time it was written. You can check " +
            "out this poem for yourself in this wikipedia article. As the article mentions, this order " +
            "is still sometimes used in ordering lists so you may want to spend some time checking it out.\n\n";

    String text4 = "Notes\n\n";
    String text5 = "1.\tExcept for **「し」**、**「ち」**、**「つ」**、and **「ん」**、you can get a sense of how each letter " +
            "is pronounced by matching the consonant on the top row to the vowel. For example, " +
            "**「き」** would become / ki / and **「ゆ」** would become / yu / and so on.\n";
    String text6 = "2.\tAs you can see, not all sounds match the way our consonant system works. " +
            "As written in the table, **「ち」** is pronounced \"chi\" and **「つ」** is pronounced \"tsu\".";
    String text7 = "3.\tThe / r / or / l / sound in Japanese is quite different from any sound in " +
            "English. It involves more of a roll and a clip by hitting the roof of your mouth with" +
            " your tongue. Pay careful attention to that whole column.";
    String text8 = "4.\tPay careful attention to the difference between / tsu / and / su /.\n";
    String text9 = "5.\t The **「ん」** character is a special character because it is rarely used by " +
            "itself and does not have a vowel sound. It is attached to another character to " +
            "add a / n / sound. For example, **「かん」** becomes 'kan' instead of 'ka', **「まん」** becomes " +
            "'man' instead of 'ma', and so on and so forth.";
    String text10 = "6.\tYou must learn the correct stroke order and direction! " +
            "Use the following pdf practice sheets. " +
            "**http://japanese-lesson.com/characters/hiragana/hiragana_writing.html**";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wiki_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.wikipage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConstraintLayout btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView imageView = findViewById(R.id.imageView11);

        // Создание ColorMatrix для инвертирования цветов
        float[] matrix = {
                -1,  0,  0,  0, 255,
                0, -1,  0,  0, 255,
                0,  0, -1,  0, 255,
                0,  0,  0,  1,   0
        };
        ColorMatrix colorMatrix = new ColorMatrix(matrix);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);

        // Применение ColorFilter к ImageView
        imageView.setColorFilter(colorFilter);

        namePage = findViewById(R.id.namePage);
        namePage.setText("Hiragana");

        mainText = findViewById(R.id.mainText);
        mainText.setText(text1 + text2 + text3 + text4 + text5 + text6 + text7 + text8 + text9 + text10);

        new TextHighlighter(mainText)
                .addWord("Hiragana", Color.parseColor("#FF5722"),
                        "is the basic Japanese phonetic script. " +
                        "It represents every sound in the Japanese language")
                .addWord("「いろは」", Color.parseColor("#2196F3"), "Iroha")
                .addWord("「し」", Color.parseColor("#2196F3"), "Ji")
                .addWord("「ち」", Color.parseColor("#2196F3"), "Chi")
                .addWord("「つ」", Color.parseColor("#2196F3"), "Tsu")
                .addWord("「ん」", Color.parseColor("#2196F3"), "n")
                .addWord("「き」", Color.parseColor("#2196F3"), "Ki")
                .addWord("「ゆ」", Color.parseColor("#2196F3"), "Yu")
                .addWord("「かん」", Color.parseColor("#2196F3"), "Kan")
                .addWord("「まん」", Color.parseColor("#2196F3"), "Man")
                .addWord("http://japanese-lesson.com/characters/hiragana/hiragana_writing.html",
                        Color.parseColor("#ff0000"), "Link to materials")
                .apply();
    }
}