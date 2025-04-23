package com.example.sportsque;

import static android.graphics.Color.BLACK;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Spinner in the layout
        Spinner sportsSpinner = findViewById(R.id.sportsSpinner);

        // Array of sports
        String[] sports = {"Select a Sport", "Football", "Cricket", "Basketball", "Tennis", "Badminton"};

        // Creating an ArrayAdapter to populate the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sports);

        // Set adapter to Spinner
        sportsSpinner.setAdapter(adapter);

        // Set item selected listener for the Spinner
        sportsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Display a message when a sport is selected (excluding first item)
                if (position != 0) {
                    String selectedSport = parent.getItemAtPosition(position).toString();
                    Toast.makeText(MainActivity.this, "Selected Sport: " + selectedSport, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }


}
