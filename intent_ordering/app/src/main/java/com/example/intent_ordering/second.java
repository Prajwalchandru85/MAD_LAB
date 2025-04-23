package com.example.intent_ordering;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class second extends AppCompatActivity {
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewResult = findViewById(R.id.textViewResult);

        String name = getIntent().getStringExtra("name");
        String gender = getIntent().getStringExtra("gender");
        String choice = getIntent().getStringExtra("choice");

        textViewResult.setText("Name: " + name + "\nGender: " + gender + "\nChoice: " + choice);
    }
}
