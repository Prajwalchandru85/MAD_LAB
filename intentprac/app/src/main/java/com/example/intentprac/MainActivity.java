package com.example.intentprac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.ed1);
        et2=findViewById(R.id.ed2);
        btn=findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = et1.getText().toString();
                String num = et2.getText().toString();
                 Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                 intent.putExtra("username",n);
                 intent.putExtra("number",num);
                 startActivity(intent);



            }
        });

    }
}