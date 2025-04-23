package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button
        Button buttonNavigate = findViewById(R.id.buttonNavigate);

        // Set click listener to navigate to SecondActivity
        buttonNavigate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, second.class);
            startActivity(intent);
        });
    }
}
