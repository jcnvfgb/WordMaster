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
import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.SetModel;
import com.example.maing.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SettingUpSets extends AppCompatActivity {
    FloatingActionButton floatingId;
    RecyclerView recyclerView;
    ArrayList<SetModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting_up_sets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        floatingId = findViewById(R.id.floatingId);
        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getAllSets();
        while (cursor.moveToNext()) {
            arrayList.add(new SetModel(cursor.getString(1),
                    cursor.getInt(0),
                    cursor.getInt(2)));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetAdapter adapter = new SetAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        floatingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_len = getIntent().getIntExtra("id", 0);
                Log.e("SettingUpSets", "id_language  " + id_len);
                //startActivity(new Intent(SettingUpSets.this, MainActivity2.class));
            }
        });
    }
}