package com.example.projtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimeframeActivity extends AppCompatActivity {

    private SeekBar timeframeSeekBar;
    private TextView timeframeTextView;
    private double income;
    private String goal;
    private String riskProfile;
    private int timeframeYears = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeframe);

        timeframeSeekBar = findViewById(R.id.timeframeSeekBar);
        timeframeTextView = findViewById(R.id.timeframeTextView);
        Button calculateButton = findViewById(R.id.calculateButton);

        // Get data from previous activity
        income = getIntent().getDoubleExtra("income", 0);
        goal = getIntent().getStringExtra("goal");
        riskProfile = getIntent().getStringExtra("risk");

        timeframeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeframeYears = progress + 1; // Minimum 1 year
                timeframeTextView.setText(timeframeYears + " years");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeframeActivity.this, ResultsActivity.class);
                intent.putExtra("income", income);
                intent.putExtra("goal", goal);
                intent.putExtra("risk", riskProfile);
                intent.putExtra("timeframe", timeframeYears);
                startActivity(intent);
            }
        });
    }
}