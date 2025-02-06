package com.example.maing.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

public class SetManagement extends AppCompatActivity {
    EditText updateSetTitle;
    TextView updateSetBtn, deleteSetBtn;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.UpdateSet), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(SetManagement.this);
        updateSetBtn = findViewById(R.id.UpdateSetBtn);
        deleteSetBtn = findViewById(R.id.DeleteSetBtn);
        updateSetTitle = findViewById(R.id.UpdateSetTitle);

        int id_len = getIntent().getIntExtra("id_lan", 0);
        int id_set = getIntent().getIntExtra("id_set", 0);
        String set_name = getIntent().getStringExtra("setName");

        updateSetTitle.setText(set_name);

        updateSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (updateSetTitle.length() > 0) {
                    databaseHelper.updateSet(
                            id_set,
                            updateSetTitle.getText().toString(),
                            id_len
                    );
                    Toast.makeText(SetManagement.this,
                            "The data successfully changed",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        deleteSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteSet(id_set);
                Toast.makeText(SetManagement.this,
                        "The data successfully delete",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}