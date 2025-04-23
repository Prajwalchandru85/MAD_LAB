package com.example.xyz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button rbtn, bbtn, gbtn, ybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        bbtn = findViewById(R.id.button);
        gbtn = findViewById(R.id.button2);
        rbtn = findViewById(R.id.button3);
        ybtn = findViewById(R.id.button4);

        // Make TextView editable when clicked
        tv.setOnClickListener(v -> {
            tv.setFocusableInTouchMode(true);  // Enable focus for the TextView
            tv.setFocusable(true);  // Allow the user to input text
            tv.requestFocus();  // Make sure the TextView gets the focus
        });

        // Set a listener to listen for the "Enter" (OK) key press
        tv.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                // When "OK" (Enter) is pressed, the TextView is updated
                tv.setText(tv.getText().toString());
                tv.setFocusable(false);  // Disable focus after editing is done
                return true;
            }
            return false;
        });

        // Change text color based on button clicks
        bbtn.setOnClickListener(v -> tv.setTextColor(Color.BLUE));
        gbtn.setOnClickListener(v -> tv.setTextColor(Color.GREEN));
        rbtn.setOnClickListener(v -> tv.setTextColor(Color.RED));
        ybtn.setOnClickListener(v -> tv.setTextColor(Color.YELLOW));
    }
}
