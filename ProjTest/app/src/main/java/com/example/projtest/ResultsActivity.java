package com.example.projtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private TextView allocationTextView;
    private TextView returnTextView;
    private TextView recommendationsTextView;
    private ProgressBar loadingProgressBar;

    private double income;
    private String goal;
    private String riskProfile;
    private int timeframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        allocationTextView = findViewById(R.id.allocationTextView);
        returnTextView = findViewById(R.id.returnTextView);
        recommendationsTextView = findViewById(R.id.recommendationsTextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        Button showFundsButton = findViewById(R.id.showFundsButton);

        // Get data from previous activity
        income = getIntent().getDoubleExtra("income", 0);
        goal = getIntent().getStringExtra("goal");
        riskProfile = getIntent().getStringExtra("risk");
        timeframe = getIntent().getIntExtra("timeframe", 1);

        // Calculate recommended allocation
        calculateResults();

        showFundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                fetchMutualFundRecommendations();
            }
        });
    }

    private void calculateResults() {
        // Get asset allocation based on risk and timeframe
        Map<String, Double> allocation = InvestmentCalculator.getAssetAllocation(riskProfile, timeframe);

        // Calculate the amount to invest monthly (assuming 30% of income should be saved)
        double monthlyInvestment = income * 0.3;

        // Calculate expected returns
        double expectedAnnualReturn = InvestmentCalculator.getExpectedReturn(allocation);
        double futureValue = InvestmentCalculator.calculateFutureValue(
                monthlyInvestment * 12, expectedAnnualReturn, timeframe, 1);

        // Format the results
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

        StringBuilder allocationText = new StringBuilder();
        allocationText.append("Monthly Investment: ").append(currencyFormat.format(monthlyInvestment)).append("\n\n");
        allocationText.append("Recommended Allocation:\n");

        for (Map.Entry<String, Double> entry : allocation.entrySet()) {
            String assetType = entry.getKey();
            double percentage = entry.getValue();
            double amount = monthlyInvestment * (percentage / 100);

            allocationText.append(assetType).append(": ").append(String.format("%.1f%%", percentage));
            allocationText.append(" (").append(currencyFormat.format(amount)).append(" per month)\n");
        }

        String returnText = "Expected annual return: " + String.format("%.2f%%", expectedAnnualReturn) + "\n";
        returnText += "Projected value after " + timeframe + " years: " + currencyFormat.format(futureValue);

        allocationTextView.setText(allocationText.toString());
        returnTextView.setText(returnText);
    }

    private void fetchMutualFundRecommendations() {
        ApiService apiService = com.example.projtest.RetrofitClient.getClient().create(ApiService.class);

        Call<AlphaVantageResponse> call = apiService.getMutualFundData("PNL3P2JRW3LU5GCH", "VFINX");
        call.enqueue(new Callback<AlphaVantageResponse>() {
            @Override
            public void onResponse(Call<AlphaVantageResponse> call, Response<AlphaVantageResponse> response) {
                loadingProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    AlphaVantageResponse data = response.body();
                    displayMutualFundData(data);
                } else {
                    Toast.makeText(ResultsActivity.this, "Failed to fetch mutual fund data", Toast.LENGTH_SHORT).show();
                    recommendationsTextView.setText("Could not retrieve mutual fund recommendations. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<AlphaVantageResponse> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(ResultsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                recommendationsTextView.setText("Network error. Please check your connection and try again.");
            }
        });
    }

    private void displayMutualFundData(AlphaVantageResponse data) {
        StringBuilder fundsText = new StringBuilder();
        fundsText.append("Recommended Mutual Funds:\n\n");

        // For a real app, you should filter funds based on user's risk profile and goals
        // This is just a simple example showing how to display the data
        if (data.getMetaData() != null) {
            fundsText.append("Fund: ").append(data.getMetaData().getSymbol()).append("\n");
            fundsText.append("Last Updated: ").append(data.getMetaData().getLastRefreshed()).append("\n\n");
        }

        // Get risk-appropriate fund recommendations
        String[] fundRecommendations = getFundRecommendations(riskProfile, goal);

        fundsText.append("Based on your ").append(riskProfile).append(" risk profile and ");
        fundsText.append(goal).append(" goal, we recommend:\n\n");

        for (String fund : fundRecommendations) {
            fundsText.append("• ").append(fund).append("\n");
        }

        recommendationsTextView.setText(fundsText.toString());
    }

    private String[] getFundRecommendations(String riskProfile, String goal) {
        // In a real app, this would query an API or database
        // This is a simple example with hardcoded recommendations

        if ("Low Risk".equals(riskProfile)) {
            return new String[] {
                    "HDFC Banking and PSU Debt Fund",
                    "SBI Corporate Bond Fund",
                    "Kotak Corporate Bond Fund"
            };
        } else if ("Medium Risk".equals(riskProfile)) {
            return new String[] {
                    "Axis Bluechip Fund",
                    "ICICI Prudential Balanced Advantage Fund",
                    "Kotak Equity Hybrid Fund"
            };
        } else {
            return new String[] {
                    "Mirae Asset Emerging Bluechip Fund",
                    "Axis Small Cap Fund",
                    "DSP Small Cap Fund"
            };
        }
    }
}