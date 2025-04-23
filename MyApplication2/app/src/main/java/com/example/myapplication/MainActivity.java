package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout = findViewById(R.id.layout);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fix: Correctly reference the layout
//        RelativeLayout layout = findViewById(R.id.layout);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layout.setBackgroundColor(Color.BLACK); // Change to black
            } else {
                layout.setBackgroundColor(Color.WHITE); // Change to white
            }
        });
    }
}
