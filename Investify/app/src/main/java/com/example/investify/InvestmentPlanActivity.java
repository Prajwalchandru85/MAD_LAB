package com.example.investify;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investify.api.AlphaVantageApi;
import com.example.investify.api.ApiClient;
import com.example.investify.model.MutualFund;
import com.example.investify.model.Stock;
import com.example.investify.model.TimeSeriesResponse;
import com.example.investify.model.TopGainersLosersResponse;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestmentPlanActivity extends AppCompatActivity {

    private Spinner spinnerGoal, spinnerRiskTolerance;
    private EditText editTextPeriod;
    private Button btnCalculate;
    private TextView textViewAllocation, textViewRecommendedFunds, textViewTrendingStocks;
    private TextView textViewMonthlyInvestment, textViewFutureValue;
    private RecyclerView recyclerViewFunds, recyclerViewStocks;
    private ProgressBar progressBar;

    private AlphaVantageApi apiService;
    private final String API_KEY = "KXUTPTE9HXLP2MOC";

    // Get income from intent
    private double income = 0;

    // Symbols for different risk categories
    private final String[] conservativeSymbols = {"VBMFX", "VFINX", "VGSLX","FTGGX"};
    private final String[] moderateSymbols = {"VFINX", "VIMSX", "VGTSX"};
    private final String[] aggressiveSymbols = {"VIMSX", "NAESX", "VGTSX"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_plan);

        // Get income from intent
        income = getIntent().getDoubleExtra("income", 0);

        // Initialize API service
        apiService = ApiClient.getClient().create(AlphaVantageApi.class);

        // Initialize UI elements
        spinnerGoal = findViewById(R.id.spinnerGoal);
        spinnerRiskTolerance = findViewById(R.id.spinnerRiskTolerance);
        editTextPeriod = findViewById(R.id.editTextPeriod);
        btnCalculate = findViewById(R.id.btnCalculate);
        textViewAllocation = findViewById(R.id.textViewAllocation);
        textViewRecommendedFunds = findViewById(R.id.textViewRecommendedFunds);
        textViewTrendingStocks = findViewById(R.id.textViewTrendingStocks);
        textViewMonthlyInvestment = findViewById(R.id.textViewMonthlyInvestment);
        textViewFutureValue = findViewById(R.id.textViewFutureValue);
        recyclerViewFunds = findViewById(R.id.recyclerViewFunds);
        recyclerViewStocks = findViewById(R.id.recyclerViewStocks);
        progressBar = findViewById(R.id.progressBar);

        // Setup spinners
        setupSpinners();

        // Setup RecyclerViews
        recyclerViewFunds.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStocks.setLayoutManager(new LinearLayoutManager(this));

        // Set click listener for calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInvestmentPlan();
            }
        });
    }

    private void setupSpinners() {
        // Setup goal spinner
        ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(
                this, R.array.investment_goals, android.R.layout.simple_spinner_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(goalAdapter);

        // Setup risk tolerance spinner
        ArrayAdapter<CharSequence> riskAdapter = ArrayAdapter.createFromResource(
                this, R.array.risk_tolerance, android.R.layout.simple_spinner_item);
        riskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRiskTolerance.setAdapter(riskAdapter);
    }

    private void calculateInvestmentPlan() {
        // Get input values
        String goal = spinnerGoal.getSelectedItem().toString();
        String riskTolerance = spinnerRiskTolerance.getSelectedItem().toString();
        String periodStr = editTextPeriod.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(periodStr)) {
            editTextPeriod.setError("Please enter investment period");
            editTextPeriod.requestFocus();
            return;
        }

        int period;
        try {
            period = Integer.parseInt(periodStr);
        } catch (NumberFormatException e) {
            editTextPeriod.setError("Please enter a valid number");
            editTextPeriod.requestFocus();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Calculate monthly investment (30% of income)
        double monthlyInvestment = income * 0.3;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        textViewMonthlyInvestment.setText("Recommended Monthly Investment: " + currencyFormat.format(monthlyInvestment));
        textViewMonthlyInvestment.setVisibility(View.VISIBLE);

        // Get asset allocation based on risk and timeframe
        Map<String, Double> allocation = getAssetAllocation(riskTolerance, period);

        // Calculate expected return
        double expectedReturn = getExpectedReturn(allocation);

        // Calculate future value
        double futureValue = calculateFutureValue(monthlyInvestment * 12, expectedReturn, period, 1);

        textViewFutureValue.setText("Projected value after " + period + " years: " + currencyFormat.format(futureValue));
        textViewFutureValue.setVisibility(View.VISIBLE);

        // Display allocation
        StringBuilder allocationText = new StringBuilder("Recommended Asset Allocation:\n\n");
        for (Map.Entry<String, Double> entry : allocation.entrySet()) {
            String assetType = entry.getKey();
            double percentage = entry.getValue();
            double amount = monthlyInvestment * (percentage / 100);
            allocationText.append(assetType).append(": ").append(String.format("%.1f%%", percentage));
            allocationText.append(" (").append(currencyFormat.format(amount)).append(" per month)\n");
        }

        textViewAllocation.setText(allocationText.toString());
        textViewAllocation.setVisibility(View.VISIBLE);

        // Fetch recommended mutual funds based on risk tolerance
        fetchRecommendedFunds(riskTolerance);

        // Fetch trending stocks from Alpha Vantage API
        fetchTrendingStocks();
    }

    private Map<String, Double> getAssetAllocation(String riskProfile, int timeHorizon) {
        Map<String, Double> allocation = new HashMap<>();

        // Base allocations by risk profile
        if ("Conservative (Low Risk)".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 50.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 15.0);
            allocation.put("Stocks", 5.0);
        } else if ("Moderate (Medium Risk)".equals(riskProfile)) {
            allocation.put("Fixed Deposits", 30.0);
            allocation.put("Debt Mutual Funds", 30.0);
            allocation.put("Equity Mutual Funds", 25.0);
            allocation.put("Stocks", 15.0);
        } else { // High Risk
            allocation.put("Fixed Deposits", 10.0);
            allocation.put("Debt Mutual Funds", 20.0);
            allocation.put("Equity Mutual Funds", 40.0);
            allocation.put("Stocks", 30.0);
        }

        // Adjust based on time horizon
        adjustForTimeHorizon(allocation, timeHorizon);

        return allocation;
    }

    private void adjustForTimeHorizon(Map<String, Double> allocation, int timeHorizon) {
        double equityAdjustment = 0.0;

        if (timeHorizon < 3) {
            // Short-term: reduce equity and stocks
            equityAdjustment = -10.0;
        } else if (timeHorizon >= 7) {
            // Long-term: increase equity and stocks
            equityAdjustment = 10.0;
        }

        // Apply adjustment (ensuring allocations stay in reasonable ranges)
        if (equityAdjustment != 0) {
            double equity = allocation.get("Equity Mutual Funds");
            double stocks = allocation.get("Stocks");
            double fd = allocation.get("Fixed Deposits");

            // Adjust equity and stocks
            double newEquity = Math.max(10.0, Math.min(60.0, equity + equityAdjustment/2));
            double newStocks = Math.max(5.0, Math.min(40.0, stocks + equityAdjustment/2));

            double totalAdjustment = (newEquity - equity) + (newStocks - stocks);

            allocation.put("Equity Mutual Funds", newEquity);
            allocation.put("Stocks", newStocks);

            // Take the adjustment from FDs
            double newFd = Math.max(5.0, fd - totalAdjustment);
            allocation.put("Fixed Deposits", newFd);

            // Ensure the total is still 100%
            double total = 0;
            for (Double value : allocation.values()) {
                total += value;
            }
            double debt = allocation.get("Debt Mutual Funds");
            allocation.put("Debt Mutual Funds", debt + (100 - total));
        }
    }

    private double getExpectedReturn(Map<String, Double> allocation) {
        // Estimated returns for each asset class
        double fdReturn = 6.0; // 6% for FDs
        double debtReturn = 8.0; // 8% for debt funds
        double equityReturn = 12.0; // 12% for equity funds
        double stocksReturn = 15.0; // 15% for individual stocks

        double weightedReturn = 0.0;

        // Calculate weighted average return
        if (allocation.containsKey("Fixed Deposits")) {
            weightedReturn += (allocation.get("Fixed Deposits") / 100) * fdReturn;
        }

        if (allocation.containsKey("Debt Mutual Funds")) {
            weightedReturn += (allocation.get("Debt Mutual Funds") / 100) * debtReturn;
        }

        if (allocation.containsKey("Equity Mutual Funds")) {
            weightedReturn += (allocation.get("Equity Mutual Funds") / 100) * equityReturn;
        }

        if (allocation.containsKey("Stocks")) {
            weightedReturn += (allocation.get("Stocks") / 100) * stocksReturn;
        }

        return weightedReturn;
    }

    private double calculateFutureValue(double principal, double rate, int years, int compoundingPerYear) {
        double r = rate / 100.0;
        int n = compoundingPerYear;
        int t = years;
        return principal * Math.pow(1 + (r/n), n*t);
    }

    private void fetchRecommendedFunds(String riskTolerance) {
        // Select symbols based on risk tolerance
        String[] symbols;
        String riskLevel;

        switch (riskTolerance) {
            case "Conservative (Low Risk)":
                symbols = conservativeSymbols;
                riskLevel = "Low";
                break;
            case "Moderate (Medium Risk)":
                symbols = moderateSymbols;
                riskLevel = "Moderate";
                break;
            default:
                symbols = aggressiveSymbols;
                riskLevel = "High";
                break;
        }

        final List<MutualFund> funds = new ArrayList<>();
        final Map<String, String> fundCategories = new HashMap<>();
        fundCategories.put("VBMFX", "Bond Index Fund");
        fundCategories.put("VFINX", "S&P 500 Index Fund");
        fundCategories.put("VGSLX", "REIT Index Fund");
        fundCategories.put("VIMSX", "Mid-Cap Index Fund");
        fundCategories.put("VGTSX", "Total International Stock Index Fund");
        fundCategories.put("NAESX", "Small-Cap Index Fund");

        // Counter for API calls completed
        final int[] completedCalls = {0};

        for (final String symbol : symbols) {
            // Make API call to get time series data for each symbol
            Call<TimeSeriesResponse> call = apiService.getTimeSeriesMonthly(
                    "TIME_SERIES_MONTHLY", symbol, API_KEY);

            call.enqueue(new Callback<TimeSeriesResponse>() {
                @Override
                public void onResponse(Call<TimeSeriesResponse> call, Response<TimeSeriesResponse> response) {
                    completedCalls[0]++;

                    if (response.isSuccessful() && response.body() != null && response.body().getMonthlyTimeSeries() != null) {
                        // Calculate returns based on historical data
                        Map<String, TimeSeriesResponse.TimeSeriesData> timeSeries = response.body().getMonthlyTimeSeries();

                        if (timeSeries.size() >= 12) {
                            // Get keys (dates) and sort them
                            List<String> dates = new ArrayList<>(timeSeries.keySet());
                            java.util.Collections.sort(dates, java.util.Collections.reverseOrder());

                            // Get current price and price from 12 months ago
                            double currentPrice = Double.parseDouble(timeSeries.get(dates.get(0)).getClose());
                            double oldPrice = Double.parseDouble(timeSeries.get(dates.get(11)).getClose());

                            // Calculate annual return
                            double annualReturn = ((currentPrice - oldPrice) / oldPrice) * 100;
                            String returnStr = String.format("%.2f%%", annualReturn);

                            // Add fund to the list
                            funds.add(new MutualFund(
                                    symbol,
                                    fundCategories.containsKey(symbol) ? fundCategories.get(symbol) : "Index Fund",
                                    returnStr,
                                    riskLevel
                            ));
                        }
                    }

                    // If all API calls are completed, update the UI
                    if (completedCalls[0] == symbols.length) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!funds.isEmpty()) {
                                    // Display funds
                                    MutualFundAdapter adapter = new MutualFundAdapter(funds);
                                    recyclerViewFunds.setAdapter(adapter);
                                    textViewRecommendedFunds.setVisibility(View.VISIBLE);
                                    recyclerViewFunds.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(InvestmentPlanActivity.this,
                                            "Could not fetch fund data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<TimeSeriesResponse> call, Throwable t) {
                    completedCalls[0]++;

                    // If all API calls are completed, update the UI
                    if (completedCalls[0] == symbols.length && !funds.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MutualFundAdapter adapter = new MutualFundAdapter(funds);
                                recyclerViewFunds.setAdapter(adapter);
                                textViewRecommendedFunds.setVisibility(View.VISIBLE);
                                recyclerViewFunds.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            });
        }
    }

    private void fetchTrendingStocks() {
        // Make API call to Alpha Vantage to get top gainers/losers
        Call<TopGainersLosersResponse> call = apiService.getTopGainersLosers(
                "TOP_GAINERS_LOSERS", API_KEY);

        call.enqueue(new Callback<TopGainersLosersResponse>() {
            @Override
            public void onResponse(Call<TopGainersLosersResponse> call, Response<TopGainersLosersResponse> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<TopGainersLosersResponse.StockItem> mostActive = response.body().getMostActivelyTraded();
                    if (mostActive != null && !mostActive.isEmpty()) {
                        List<Stock> stocks = new ArrayList<>();
                        for (TopGainersLosersResponse.StockItem item : mostActive) {
                            stocks.add(new Stock(
                                    item.getTicker(),
                                    item.getTicker(), // Using ticker as name since API doesn't provide company name
                                    item.getPrice(),
                                    item.getChangePercentage()
                            ));
                        }

                        // Display stocks
                        StockAdapter adapter = new StockAdapter(stocks);
                        recyclerViewStocks.setAdapter(adapter);
                        textViewTrendingStocks.setVisibility(View.VISIBLE);
                        recyclerViewStocks.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(InvestmentPlanActivity.this,
                                "No trending stocks data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InvestmentPlanActivity.this,
                            "Failed to fetch trending stocks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopGainersLosersResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InvestmentPlanActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
