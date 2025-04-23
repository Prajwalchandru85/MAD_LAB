package com.example.projtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {

    private EditText incomeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        incomeEditText = findViewById(R.id.incomeEditText);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    double income = Double.parseDouble(incomeEditText.getText().toString());

                    Intent intent = new Intent(IncomeActivity.this, GoalActivity.class);
                    intent.putExtra("income", income);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateInput() {
        String incomeStr = incomeEditText.getText().toString();
        if (incomeStr.isEmpty()) {
            Toast.makeText(this, "Please enter your income", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double income = Double.parseDouble(incomeStr);
            if (income <= 0) {
                Toast.makeText(this, "Income must be greater than zero", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}