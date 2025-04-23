package com.example.trial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText ageInput;
    private EditText incomeInput;
    private EditText goalAmountInput;
    private EditText timeHorizonInput;
    private RadioGroup riskToleranceGroup;
    private Button calculateButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        ageInput = findViewById(R.id.ageInput);
        incomeInput = findViewById(R.id.incomeInput);
        goalAmountInput = findViewById(R.id.goalAmountInput);
        timeHorizonInput = findViewById(R.id.timeHorizonInput);
        riskToleranceGroup = findViewById(R.id.riskToleranceGroup);
        calculateButton = findViewById(R.id.calculateButton);
        resultView = findViewById(R.id.resultView);

        calculateButton.setOnClickListener(v -> calculateInvestmentPlan());
    }

    private void calculateInvestmentPlan() {
        try {
            // Get user inputs
            int age = Integer.parseInt(ageInput.getText().toString());
            double income = Double.parseDouble(incomeInput.getText().toString());
            double goalAmount = Double.parseDouble(goalAmountInput.getText().toString());
            int timeHorizon = Integer.parseInt(timeHorizonInput.getText().toString());

            RiskTolerance riskTolerance;
            int selectedId = riskToleranceGroup.getCheckedRadioButtonId();

            if (selectedId == R.id.riskLow) {
                riskTolerance = RiskTolerance.LOW;
            } else if (selectedId == R.id.riskMedium) {
                riskTolerance = RiskTolerance.MEDIUM;
            } else if (selectedId == R.id.riskHigh) {
                riskTolerance = RiskTolerance.HIGH;
            } else {
                riskTolerance = RiskTolerance.MEDIUM;
            }

            // Create investment profile
            InvestmentProfile profile = new InvestmentProfile(age, income, goalAmount, timeHorizon, riskTolerance);

            // Generate and display investment plan
            InvestmentPlan plan = new InvestmentPlanner().generatePlan(profile);
            displayPlan(plan);
        } catch (NumberFormatException e) {
            resultView.setText("Please enter valid numbers in all fields");
        }
    }

    private void displayPlan(InvestmentPlan plan) {
        StringBuilder result = new StringBuilder();
        result.append("Monthly Investment Required: ₹").append(String.format("%.2f", plan.getMonthlyInvestment()))
                .append("\n\nRecommended Asset Allocation:\n\n");

        for (AssetAllocation allocation : plan.getAllocations()) {
            result.append(allocation.getAssetType())
                    .append(": ")
                    .append(allocation.getPercentage())
                    .append("%\n");
        }

        result.append("\nProjected Returns: ").append(String.format("%.1f", plan.getProjectedReturns())).append("%");
        resultView.setText(result.toString());
    }
}