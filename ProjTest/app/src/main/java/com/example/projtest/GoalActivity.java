package com.example.projtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GoalActivity extends AppCompatActivity {

    private static final String TAG = "GoalActivity";
    private RadioGroup goalRadioGroup;
    private double income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalRadioGroup = findViewById(R.id.goalRadioGroup);
        Button nextButton = findViewById(R.id.nextButton);

        // Check if RadioGroup is properly initialized
        if (goalRadioGroup == null) {
            Log.e(TAG, "RadioGroup not found in layout");
            Toast.makeText(this, "Error initializing the app", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get income from previous activity
        income = getIntent().getDoubleExtra("income", 0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSelection()) {
                    int selectedId = goalRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedButton = findViewById(selectedId);

                    if (selectedButton != null) {
                        String goal = selectedButton.getText() != null ?
                                selectedButton.getText().toString() : "";

                        // Ensure goal is not null before passing it to intent
                        if (goal != null && !goal.isEmpty()) {
                            Intent intent = new Intent(GoalActivity.this, RiskActivity.class);
                            intent.putExtra("income", income);
                            intent.putExtra("goal", goal);
                            startActivity(intent);
                        } else {
                            Toast.makeText(GoalActivity.this, "Invalid goal selection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GoalActivity.this, "Error selecting goal option", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateSelection() {
        if (goalRadioGroup == null || goalRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select an investment goal", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
