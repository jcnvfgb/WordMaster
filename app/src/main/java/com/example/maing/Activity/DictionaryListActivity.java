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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.R;

import java.util.ArrayList;

public class DictionaryListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterDictionaryList;
    private RecyclerView recyclerViewDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary_list);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        //initRecyclerView();

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

    /*private void initRecyclerView() {
        ArrayList<DictionaryDomain> items = new ArrayList<>();
        items.add(new DictionaryDomain("English", 12, "ic_1"));
        items.add(new DictionaryDomain("Spanish", 49, "ic_2"));
        items.add(new DictionaryDomain("Japanese", 557, "ic_3"));

        recyclerViewDictionary = findViewById(R.id.view);
        recyclerViewDictionary.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        adapterDictionaryList = new DictionaryAdapter(items);
        recyclerViewDictionary.setAdapter(adapterDictionaryList);
    }*/
}