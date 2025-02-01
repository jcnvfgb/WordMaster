package com.example.maing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.R;

public class AddingNewSet extends AppCompatActivity {
    EditText edTitle;
    TextView button;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adding_new_set);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTitle = findViewById(R.id.editTitle);
        button = findViewById(R.id.addButton);
        databaseHelper = new DatabaseHelper(AddingNewSet.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edTitle.length() > 0) {
                    int id_len = getIntent().getIntExtra("id", 0);
                    Log.e("SettingUpSets", "id_language  " + id_len);
                    databaseHelper.insertSet(edTitle.getText().toString(), id_len);
                    Toast.makeText(AddingNewSet.this, "The Data Added", Toast.LENGTH_SHORT).show();
                    edTitle.setText("");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddingNewSet.this, "The data wasn't added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}