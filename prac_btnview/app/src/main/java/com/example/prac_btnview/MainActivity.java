package com.example.prac_btnview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button submit;  // Declare submit button

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et);
        submit = findViewById(R.id.button4);  // Initialize submit button

        int[] idbtn = {R.id.button1, R.id.button2, R.id.button3};
        for (int id : idbtn) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button clickedbtn = (Button) v;
                    String btnText = clickedbtn.getText().toString();
                    editText.append(btnText);
                }
            });
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nums = editText.getText().toString();

                if (nums.length() != 5) {
                    Toast.makeText(MainActivity.this, "Enter exactly 5 numbers", Toast.LENGTH_SHORT).show();
                    return; // Stop execution if input is not 5 digits
                }

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("nums", nums);
                startActivity(intent);
            }
        });

    }
}
