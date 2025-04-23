package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ToggleButton and Layout
        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        RelativeLayout layout = findViewById(R.id.mainLayout);

        // Toggle background color on button click
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layout.setBackgroundColor(Color.BLACK); // When ON, change to black
            } else {
                layout.setBackgroundColor(Color.WHITE); // When OFF, change to white
            }
        });
    }
}
