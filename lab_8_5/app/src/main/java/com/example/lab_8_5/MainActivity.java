package com.example.lab_8_5;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LinearLayout fieldsContainer;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "CustomData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldsContainer = findViewById(R.id.fieldsContainer);
        MaterialButton addButton = findViewById(R.id.addButton);
        MaterialButton saveButton = findViewById(R.id.saveButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Load existing data
        loadData();

        addButton.setOnClickListener(v -> addNewField(null, null));
        saveButton.setOnClickListener(v -> saveData());
    }

    private void addNewField(String key, String value) {
        View fieldView = LayoutInflater.from(this).inflate(R.layout.key_value_pair, fieldsContainer, false);

        TextInputEditText keyInput = fieldView.findViewById(R.id.keyInput);
        TextInputEditText valueInput = fieldView.findViewById(R.id.valueInput);
        MaterialButton deleteButton = fieldView.findViewById(R.id.deleteButton);

        if (key != null) keyInput.setText(key);
        if (value != null) valueInput.setText(value);

        deleteButton.setOnClickListener(v -> fieldsContainer.removeView(fieldView));
        fieldsContainer.addView(fieldView);
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear existing data

        for (int i = 0; i < fieldsContainer.getChildCount(); i++) {
            View fieldView = fieldsContainer.getChildAt(i);
            TextInputEditText keyInput = fieldView.findViewById(R.id.keyInput);
            TextInputEditText valueInput = fieldView.findViewById(R.id.valueInput);

            String key = String.valueOf(keyInput.getText());
            String value = String.valueOf(valueInput.getText());

            if (!key.isEmpty()) {
                editor.putString(key, value);
            }
        }

        editor.apply();
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            addNewField(entry.getKey(), entry.getValue().toString());
        }

        // Add an empty field if no data exists
        if (allEntries.isEmpty()) {
            addNewField(null, null);
        }
    }
}