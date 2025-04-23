package com.example.investify;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ExpenseChartActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private String userEmail;
    private int userId;
    private PieChart pieChart;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_chart);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");

        // Get user ID from email
        if (userEmail != null) {
            userId = databaseHelper.getUserIdByEmail(userEmail);
        }

        // Initialize UI elements
        pieChart = findViewById(R.id.pieChart);
        buttonBack = findViewById(R.id.buttonBack);

        // Set up pie chart
        setupPieChart();

        // Set click listener for back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        Cursor cursor = databaseHelper.getExpenseSummaryByCategory(userId);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    int categoryNameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME);
                    int totalIndex = cursor.getColumnIndexOrThrow("total");

                    String category = cursor.getString(categoryNameIndex);
                    float total = cursor.getFloat(totalIndex);
                    entries.add(new PieEntry(total, category));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Expenses by Category");
        pieChart.animate();
    }
}
