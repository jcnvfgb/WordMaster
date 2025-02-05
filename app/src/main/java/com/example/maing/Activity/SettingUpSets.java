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
import java.util.IllegalFormatCodePointException;

public class SettingUpSets extends AppCompatActivity {
    FloatingActionButton floatingId;
    RecyclerView recyclerView;
    ArrayList<SetModel> arrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    private static final int ADD_SET_REQUEST_CODE = 1;


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

        int id_len = getIntent().getIntExtra("id", 0);
        Cursor cursor = databaseHelper.getAllSets();
        while (cursor.moveToNext()) {
            if (id_len == cursor.getInt(2)) {
                arrayList.add(new SetModel(cursor.getString(1),
                        cursor.getInt(0),
                        cursor.getInt(2)));
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SetAdapter adapter = new SetAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        floatingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_len = getIntent().getIntExtra("id", 0);
                Log.e("SettingUpSets", "id_language  " + id_len);
                Intent intent = new Intent(SettingUpSets.this, AddingNewSet.class);
                intent.putExtra("id", id_len);
                startActivityForResult(intent, ADD_SET_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SET_REQUEST_CODE && resultCode == RESULT_OK) {
            updateDataSet();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateDataSet();
    }

    private void updateDataSet() {
        arrayList.clear();
        int id_len = getIntent().getIntExtra("id", 0);
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getAllSets();
            while (cursor.moveToNext()) {
                if (id_len == cursor.getInt(2)) {
                    arrayList.add(new SetModel(cursor.getString(1),
                            cursor.getInt(0),
                            cursor.getInt(2)));
                }
            }
        } catch (Exception e) {
            Log.e("SettingUpSets", "Error fetching data ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}