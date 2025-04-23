package com.example.prac_btnview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
        TextView tv,tvv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        tv=findViewById(R.id.textView);
        tvv=findViewById(R.id.textView2);
        String n=getIntent().getStringExtra("nums");
        tv.setText(n);
        int pro = Integer.parseInt(n)*2;
       tvv.setText(String.valueOf(pro));
       // Toast.makeText(this, String.valueOf(pro), Toast.LENGTH_SHORT).show();


    }
}