package com.example.lab4basics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn=findViewByI
    d(R.id.button);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn.setOnClickListener(new View.OnClickListener(){

           @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"button clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }
}