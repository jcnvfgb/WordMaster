package com.example.maing.Activity;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maing.Adapter.StatAdapter;
import com.example.maing.Adapter.WordAdapter;
import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.StatModel;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

import java.util.ArrayList;

public class ViewStatistics extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<StatModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_statistics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.statsView);
        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getAllStats();
        while (cursor.moveToNext()) {
            arrayList.add(new StatModel(
                    cursor.getString(1),
                    cursor.getInt(0),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4)));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StatAdapter adapter = new StatAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
    }
}