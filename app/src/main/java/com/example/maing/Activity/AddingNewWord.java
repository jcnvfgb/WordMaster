package com.example.maing.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.R;

public class AddingNewWord extends AppCompatActivity {

    EditText word, wordTrans;
    Spinner spinnerWordActivity;
    TextView button;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adding_new_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addWord), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerWordActivity = findViewById(R.id.spinnerWordActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.word_activity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWordActivity.setAdapter(adapter);

        word = findViewById(R.id.editWord);
        wordTrans = findViewById(R.id.editTranslationWord);
        button = findViewById(R.id.addButtonWord);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (word.length() > 0 && wordTrans.length() > 0) {
                    int id_set = getIntent().getIntExtra("id_set", 0);
                    Log.e("AddingNewWord", "id_set  " + id_set);

                    databaseHelper.insertWord(
                            word.getText().toString().trim(),
                            wordTrans.getText().toString().trim(),
                            id_set,
                            spinnerWordActivity.getSelectedItem().toString().trim()
                    );

                    Toast.makeText(AddingNewWord.this, "The Data Added", Toast.LENGTH_SHORT).show();
                    word.setText("");
                    wordTrans.setText("");

                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddingNewWord.this, "The data wasn't added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}