package com.example.projtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RiskActivity extends AppCompatActivity {

    private RadioGroup riskRadioGroup;
    private double income;
    private String goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk);

        riskRadioGroup = findViewById(R.id.riskRadioGroup);
        Button nextButton = findViewById(R.id.nextButton);

        // Get data from previous activity
        income = getIntent().getDoubleExtra("income", 0);
        goal = getIntent().getStringExtra("goal");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSelection()) {
                    int selectedId = riskRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedButton = findViewById(selectedId);
                    String riskProfile = selectedButton.getText().toString();

                    Intent intent = new Intent(RiskActivity.this, TimeframeActivity.class);
                    intent.putExtra("income", income);
                    intent.putExtra("goal", goal);
                    intent.putExtra("risk", riskProfile);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateSelection() {
        if (riskRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select your risk tolerance", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}