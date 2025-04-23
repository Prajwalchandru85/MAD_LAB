package com.example.intentalltest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        tv1=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        tv3=findViewById(R.id.textView3);
        tv4=findViewById(R.id.textView4);
        tv5=findViewById(R.id.textView5);
        tv6=findViewById(R.id.textView6);
        String nm=getIntent().getStringExtra("name");
        String num=getIntent().getStringExtra("num");
        String foodpref=getIntent().getStringExtra("foodpref");
        String mealType=getIntent().getStringExtra("mealType");
        String payment=getIntent().getStringExtra("payment");
        String date=getIntent().getStringExtra("date");
        tv1.setText(nm);
        tv2.setText(num);
        tv3.setText(foodpref);
        tv4.setText(mealType);
        tv5.setText(payment);
        tv6.setText(date);




    }
}