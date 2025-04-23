package com.example.investify;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IncomeActivity extends AppCompatActivity {

    private EditText incomeEditText;
    private EditText budgetEditText;
    private DatabaseHelper databaseHelper;
    private String userEmail;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");

        // Get user ID from email
        if (userEmail != null) {
            userId = databaseHelper.getUserIdByEmail(userEmail);
        }

        incomeEditText = findViewById(R.id.incomeEditText);
        budgetEditText = findViewById(R.id.budgetEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button nextButton = findViewById(R.id.nextButton);

        // Load existing income and budget data if available
        loadIncomeData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    saveIncomeData();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    saveIncomeData();

                    Intent intent = new Intent(IncomeActivity.this, InvestmentPlanActivity.class);
                    intent.putExtra("income", Double.parseDouble(incomeEditText.getText().toString()));
                    intent.putExtra("EMAIL", userEmail);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadIncomeData() {
        if (userId != -1) {
            Cursor cursor = databaseHelper.getUserIncome(userId);
            if (cursor != null && cursor.moveToFirst()) {
                try {
                    int incomeColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MONTHLY_INCOME);
                    int budgetColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MONTHLY_BUDGET);

                    double monthlyIncome = cursor.getDouble(incomeColumnIndex);
                    double monthlyBudget = cursor.getDouble(budgetColumnIndex);

                    incomeEditText.setText(String.valueOf(monthlyIncome));
                    budgetEditText.setText(String.valueOf(monthlyBudget));
                } catch (IllegalArgumentException e) {
                    Toast.makeText(this, "Error loading income data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
            }
        }
    }

    private void saveIncomeData() {
        if (userId != -1) {
            double income = Double.parseDouble(incomeEditText.getText().toString());
            double budget = Double.parseDouble(budgetEditText.getText().toString());

            long result = databaseHelper.saveIncome(userId, income, budget);
            if (result > 0) {
                Toast.makeText(this, "Income and budget saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save income and budget", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        String incomeStr = incomeEditText.getText().toString();
        String budgetStr = budgetEditText.getText().toString();

        if (incomeStr.isEmpty()) {
            Toast.makeText(this, "Please enter your income", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (budgetStr.isEmpty()) {
            Toast.makeText(this, "Please enter your monthly budget", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double income = Double.parseDouble(incomeStr);
            if (income <= 0) {
                Toast.makeText(this, "Income must be greater than zero", Toast.LENGTH_SHORT).show();
                return false;
            }

            double budget = Double.parseDouble(budgetStr);
            if (budget <= 0) {
                Toast.makeText(this, "Budget must be greater than zero", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (budget > income) {
                Toast.makeText(this, "Budget should not exceed income", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
