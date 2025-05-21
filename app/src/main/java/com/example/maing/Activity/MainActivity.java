package com.example.maing.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maing.DataBase.DatabaseHelper;
import com.example.maing.Domain.DataSetPacket;
import com.example.maing.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout btn1, btn2, btn3, btn4, btn5;
    DatabaseHelper dbHelper;
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        DictionaryListActivity.class));
            }
        });

        btn1.setOnLongClickListener(view -> {
            openFileSelector();
            return true; // или false, в зависимости от логики
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = dbHelper.getBadAndMiddleWordsCount();
                if (i > 0) {
                    Log.d("WordCount", "Bad and Middle words: " + i);
                    startActivity(new Intent(MainActivity.this, BasicQuiz.class));
                } else {
                    Toast.makeText(MainActivity.this, "There are no words to learn", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FreeMode.class));
            }
        });

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DebugAct.class));
            }
        });

        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WikiPage.class));
            }
        });
    }

    private void openFileSelector() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                List<DataSetPacket> parsedData = parseFile(inputStream);
                dbHelper.importData(parsedData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<DataSetPacket> parseFile(InputStream inputStream) throws IOException {
        List<DataSetPacket> dataList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String currentLanguage = "";
        String currentSet = "";
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("##")) {
                currentSet = line.substring(2).trim();
            } else if (line.startsWith("#")) {
                currentLanguage = line.substring(1).trim();
                currentSet = ""; // Сброс набора при новом языке
            } else if (!line.isEmpty()) {
                String[] parts = line.split("\\s*-\\s*");
                if (parts.length >= 2) {
                    String word = parts[0].trim();
                    String translation = parts[1].trim();
                    dataList.add(new DataSetPacket(currentLanguage, currentSet, word, translation));
                }
            }
        }
        return dataList;
    }
}