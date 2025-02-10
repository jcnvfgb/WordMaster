package com.example.maing.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.R;

public class DebugAct extends AppCompatActivity {
    EditText command;
    AppCompatButton execute;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_debug);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.DEBUG), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        command = findViewById(R.id.command);
        execute = findViewById(R.id.execute);

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (command.getText().toString().trim().equals("Leng")) {
                    databaseHelper.insertLanguage("English");
                    databaseHelper.insertLanguage("Japanese");
                    Toast.makeText(DebugAct.this, "Languages inserted", Toast.LENGTH_SHORT).show();
                }
                if (command.getText().toString().trim().equals("DelLeng")) {
                    databaseHelper.deleteLanguage(1);
                    databaseHelper.deleteLanguage(2);
                    Toast.makeText(DebugAct.this, "Languages deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}