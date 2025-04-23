package com.example.sharedprefrence;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button buttonSave;
    TextView textViewDisplay;

    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "MyPref";
    public static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonSave = findViewById(R.id.buttonSave);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Get SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Show previously saved name (if any)
        String savedName = sharedPreferences.getString(KEY_NAME, "No name saved");
        textViewDisplay.setText("Stored Name: " + savedName);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();

            // Save to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, name);
            editor.apply();

            textViewDisplay.setText("Stored Name: " + name);
        });
    }
}
