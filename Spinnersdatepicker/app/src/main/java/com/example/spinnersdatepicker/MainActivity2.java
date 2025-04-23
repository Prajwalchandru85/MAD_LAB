package com.example.spinnersdatepicker;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        String s1 = getIntent().getStringExtra("date");
        String s2 = getIntent().getStringExtra("time");
        String s3 = getIntent().getStringExtra("spinner");
        String s4= getIntent().getStringExtra("rbb");
        String s5= getIntent().getStringExtra("cbb");
        et1.setText(s1);
        et2.setText(s2);
        et3.setText(s3);
        et4.setText(s4);
        et5.setText(s5);

    }
}