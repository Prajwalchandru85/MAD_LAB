package com.example.lviewprac;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //ListView lv; // Declare ListView globally but don't initialize it here
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.ed);
        int[] idbtn = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6};
        for (int id : idbtn) {
            Button btn = findViewById(id);

            btn.setOnClickListener(view -> onButtonClick(view));

        }
    }
    private void onButtonClick(View v)
    {
       Button clickedbtn = (Button) v;// what is this? this is a button object which is clicked why are we doing this?
        editText.append(clickedbtn.getText().toString());

        }
    }

