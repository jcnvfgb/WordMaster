package com.example.maing.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.health.connect.datatypes.StepsCadenceRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;

public class FreeMode extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ArrayList<SetModel> arrayList = new ArrayList<>();
    Spinner spinner;
    SpinerAdapter spinerAdapter;
    TextView startFreeMode;
    TextView selectMode;
    boolean[] selectedMode;
    ArrayList<Integer> modeList = new ArrayList<>();
    String[] modeArray = {"Multiple choice", "Translation", "Matching", "Expression assembly"};

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

        selectMode = findViewById(R.id.textView);

        // initialize selected language array
        selectedMode = new boolean[modeArray.length];

        selectMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(FreeMode.this);

                // set title
                builder.setTitle("Select Mode");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(modeArray, selectedMode, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            modeList.add(i);
                            // Sort array list
                            Collections.sort(modeList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            modeList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < modeList.size(); j++) {
                            // concat array value
                            stringBuilder.append(modeArray[modeList.get(j)]);
                            // check condition
                            if (j != modeList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        selectMode.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedMode.length; j++) {
                            // remove all selection
                            selectedMode[j] = false;
                            // clear language list
                            modeList.clear();
                            // clear text view value
                            selectMode.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        startFreeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedModeS = selectMode.getText().toString();
                if (selectedModeS.equals(modeArray[0])) {
                    SetModel selectedSet = (SetModel) spinner.getSelectedItem();
                    Intent intent = new Intent(FreeMode.this, BasicQuiz.class);
                    intent.putExtra("freeMode", 1);
                    intent.putExtra("idSet", selectedSet.getId_set());
                    startActivity(intent);
                    Log.d("FreeMode", "Set id: " + selectedSet.getId_set());
                    Log.d("FreeMode", "Xto take in selected rofl: " + selectMode.getText());
                    finish();
                } else {
                    Toast.makeText(FreeMode.this, "In development!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}