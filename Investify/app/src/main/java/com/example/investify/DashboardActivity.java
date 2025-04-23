
package com.example.investify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private Button btnPlanInvestments, btnExpenseTracker, btnNews;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

// Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");

// Initialize UI elements
        textViewWelcome = findViewById(R.id.textViewWelcome);
        btnPlanInvestments = findViewById(R.id.btnPlanInvestments);
        btnExpenseTracker = findViewById(R.id.btnExpenseTracker);
        btnNews = findViewById(R.id.btnNews);

// Set welcome message
        if (userEmail != null) {
            textViewWelcome.setText("Welcome, " + userEmail);
        }

// Set click listeners
        btnPlanInvestments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        btnExpenseTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ExpenseTrackerActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
// Logout and go back to main activity
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}