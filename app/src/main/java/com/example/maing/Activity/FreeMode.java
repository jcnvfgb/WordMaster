package com.example.maing.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.Adapter.SpinerAdapter;
import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.SetModel;
import com.example.maing.R;

import java.util.ArrayList;

public class FreeMode extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ArrayList<SetModel> arrayList = new ArrayList<>();
    Spinner spinner;
    SpinerAdapter spinerAdapter;
    TextView startFreeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_free_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FREEMODE), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getAllSets();
        while (cursor.moveToNext()) {
            arrayList.add(new SetModel(cursor.getString(1),
                    cursor.getInt(0),
                    cursor.getInt(2)));
        }

        spinner = findViewById(R.id.spinner);
        startFreeMode = findViewById(R.id.startFreeMode);

        spinerAdapter = new SpinerAdapter(this, arrayList);
        spinner.setAdapter(spinerAdapter);

        startFreeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetModel selectedSet = (SetModel) spinner.getSelectedItem();
                Intent intent = new Intent(FreeMode.this, BasicQuiz.class);
                intent.putExtra("freeMode", 1);
                intent.putExtra("idSet", selectedSet.getId_set());
                startActivity(intent);
                Log.d("FreeMode", "Set id: " + selectedSet.getId_set());
                finish();
            }
        });
    }
}