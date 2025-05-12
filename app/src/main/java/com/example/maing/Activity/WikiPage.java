package com.example.maing.Activity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.R;

public class WikiPage extends AppCompatActivity {

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
    }
}