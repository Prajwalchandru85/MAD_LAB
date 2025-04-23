package com.example.tabprac;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    TextView textView,textView2;
    ListView lv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
       textView=findViewById(R.id.textView);
       textView2=findViewById(R.id.textView2);
       TextView tv3=findViewById(R.id.textView3);
       ListView lv=findViewById(R.id.lv);
        Button button2=findViewById(R.id.button2);
        String name=getIntent().getStringExtra("name");
        String num=getIntent().getStringExtra("num");
        String p=getIntent().getStringExtra("prog");
        boolean check=getIntent().getBooleanExtra("check",false);
        boolean radio=getIntent().getBooleanExtra("radio",false);
        boolean toggle=getIntent().getBooleanExtra("toggle",false);
        boolean sw=getIntent().getBooleanExtra("switch",false);
        textView.setText(name);
        textView2.setText(num);
        tv3.setText(p);
        if(check){
            tv3.setText("checked");
        }

        if(toggle){
            tv3.setText("on");
        }

        if(radio){
            tv3.setText("radio");
        }
        if(sw){
            tv3.setText("switch");
          tv3.setTextColor(Color.GREEN);

        }

        String[] banks={"SBI","HDFC","ICICI","AXIS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, banks);
        lv.setAdapter(adapter);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

}



}