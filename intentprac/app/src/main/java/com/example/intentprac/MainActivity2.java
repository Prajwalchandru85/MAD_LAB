package com.example.intentprac;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
        TextView tv1,tv2;

        Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
       tv1=findViewById(R.id.ed1);
       tv2=findViewById(R.id.ed2);
       btn = findViewById(R.id.btn1);
       btn.setOnClickListener(new View.OnClickListener()
       {
           public void onClick(View v){
               Toast.makeText(MainActivity2.this,"Button Clicked",Toast.LENGTH_SHORT).show();

           }
       });
       String n=getIntent().getStringExtra("username");
       String num=getIntent().getStringExtra("number");
       tv1.setText(n);
       tv2.setText(num);


    }
}