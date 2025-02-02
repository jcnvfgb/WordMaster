package com.example.maing.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Adapter.SetAdapter;
import com.example.maing.Adapter.WordAdapter;
import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.SetModel;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SettingUpWords extends AppCompatActivity {

    FloatingActionButton floatingId;
    RecyclerView recyclerView;
    ArrayList<WordModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    private static final int ADD_SET_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting_up_words);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        floatingId = findViewById(R.id.addBtnWord);
        recyclerView = findViewById(R.id.wordsView);
        databaseHelper = new DatabaseHelper(this);

        int id_len = getIntent().getIntExtra("id_lan", 0);
        int id_set = getIntent().getIntExtra("id_set", 0);
        Cursor cursor = databaseHelper.getAllWords();
        while (cursor.moveToNext()) {
            if (id_set == cursor.getInt(3)) {
                arrayList.add(new WordModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getInt(0),
                        cursor.getInt(3)));
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WordAdapter adapter = new WordAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        floatingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingUpWords.this, AddingNewWord.class);
                intent.putExtra("id_set", id_set);
                startActivityForResult(intent, ADD_SET_REQUEST_CODE);
            }
        });
    }
}