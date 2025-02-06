package com.example.maing.Activity;

import android.os.Bundle;
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
import com.example.maing.Domain.WordModel;
import com.example.maing.R;

public class WordManagement extends AppCompatActivity {

    EditText editWordUpdate, editTranslationWordUpdate;
    Spinner spinnerWordActivityUpdate;
    TextView updateButtonWord, deleteButtonWord;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_word_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.updateDeleteWord), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WordModel item = new WordModel(
                getIntent().getStringExtra("word"),
                getIntent().getStringExtra("translation"),
                getIntent().getStringExtra("wordActivity"),
                getIntent().getIntExtra("id_word", 0),
                getIntent().getIntExtra("id_set", 0)
        );

        editWordUpdate = findViewById(R.id.editWordUpdate);
        editTranslationWordUpdate = findViewById(R.id.editTranslationWordUpdate);
        updateButtonWord = findViewById(R.id.updateButtonWord);
        deleteButtonWord = findViewById(R.id.deleteButtonWord);
        spinnerWordActivityUpdate = findViewById(R.id.spinnerWordActivityUpdate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.word_activity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWordActivityUpdate.setAdapter(adapter);

        String targetValue = item.getWordActivity();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(targetValue)) {
                spinnerWordActivityUpdate.setSelection(i);
                break;
            }
        }

        databaseHelper = new DatabaseHelper(WordManagement.this);

        editWordUpdate.setText(item.getWord());
        editTranslationWordUpdate.setText(item.getTranslation());

        updateButtonWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editWordUpdate.length() > 0 && editTranslationWordUpdate.length() > 0) {
                    databaseHelper.updateWord(
                            item.getId_word(),
                            editWordUpdate.getText().toString().trim(),
                            editTranslationWordUpdate.getText().toString().trim(),
                            item.getId_set(),
                            spinnerWordActivityUpdate.getSelectedItem().toString().trim()
                    );
                    Toast.makeText(WordManagement.this,
                            "The data successfully changed",
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        deleteButtonWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteWord(item.getId_word());
                Toast.makeText(WordManagement.this,
                        "The data successfully deleted",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}