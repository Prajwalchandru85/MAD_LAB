package com.example.investify;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseTrackerActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private String userEmail;
    private int userId;
    private double monthlyBudget = 0;
    private double totalSpent = 0;

    private TextView textViewBudgetInfo;
    private RecyclerView recyclerViewExpenses;
    private FloatingActionButton fabAddExpense;
    private Button buttonViewChart;

    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenseList;

    private Calendar selectedDate = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_tracker);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("EMAIL");

        // Get user ID from email
        if (userEmail != null) {
            userId = databaseHelper.getUserIdByEmail(userEmail);
        }

        // Initialize UI elements
        textViewBudgetInfo = findViewById(R.id.textViewBudgetInfo);
        recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);
        fabAddExpense = findViewById(R.id.fabAddExpense);
        buttonViewChart = findViewById(R.id.buttonViewChart);

        // Set up RecyclerView
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList, new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteExpense(position);
            }
        });

        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpenses.setAdapter(expenseAdapter);

        // Load budget and expenses
        loadBudgetInfo();
        loadExpenses();

        // Set click listeners
        fabAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExpenseDialog();
            }
        });

        buttonViewChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseTrackerActivity.this, ExpenseChartActivity.class);
                intent.putExtra("EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBudgetInfo();
        loadExpenses();
    }

    private void loadBudgetInfo() {
        if (userId != -1) {
            Cursor cursor = databaseHelper.getUserIncome(userId);
            if (cursor != null && cursor.moveToFirst()) {
                int budgetColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MONTHLY_BUDGET);
                monthlyBudget = cursor.getDouble(budgetColumnIndex);
                cursor.close();
            }

            // Calculate total spent
            totalSpent = 0;
            for (Expense expense : expenseList) {
                totalSpent += expense.getAmount();
            }

            // Update UI
            textViewBudgetInfo.setText(String.format("Budget: ₹%.2f / Spent: ₹%.2f", monthlyBudget, totalSpent));
        }
    }

    private void loadExpenses() {
        if (userId != -1) {
            expenseList.clear();
            Cursor cursor = databaseHelper.getAllExpenses(userId);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    try {
                        int idColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_ID);
                        int amountColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_AMOUNT);
                        int dateColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_DATE);
                        int categoryIdColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_CATEGORY);
                        int descriptionColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXPENSE_DESCRIPTION);
                        int categoryNameColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME);

                        int id = cursor.getInt(idColumnIndex);
                        double amount = cursor.getDouble(amountColumnIndex);
                        String date = cursor.getString(dateColumnIndex);
                        int categoryId = cursor.getInt(categoryIdColumnIndex);
                        String description = cursor.getString(descriptionColumnIndex);
                        String categoryName = cursor.getString(categoryNameColumnIndex);

                        Expense expense = new Expense(id, amount, date, categoryId, description, categoryName);
                        expenseList.add(expense);
                    } catch (IllegalArgumentException e) {
                        // Handle column not found exception
                        e.printStackTrace();
                    }
                }
                cursor.close();
                expenseAdapter.notifyDataSetChanged();

                // Recalculate total spent
                totalSpent = 0;
                for (Expense expense : expenseList) {
                    totalSpent += expense.getAmount();
                }

                // Update UI
                textViewBudgetInfo.setText(String.format("Budget: ₹%.2f / Spent: ₹%.2f", monthlyBudget, totalSpent));
            }
        }
    }

    private void showAddExpenseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_expense, null);
        builder.setView(dialogView);

        final EditText editTextAmount = dialogView.findViewById(R.id.editTextExpenseAmount);
        final Spinner spinnerCategory = dialogView.findViewById(R.id.spinnerCategory);
        final Button buttonSelectDate = dialogView.findViewById(R.id.buttonSelectDate);
        final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);

        // Set current date as default
        buttonSelectDate.setText(dateFormat.format(selectedDate.getTime()));

        // Set up date picker
        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ExpenseTrackerActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedDate.set(Calendar.YEAR, year);
                                selectedDate.set(Calendar.MONTH, month);
                                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                buttonSelectDate.setText(dateFormat.format(selectedDate.getTime()));
                            }
                        },
                        selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH),
                        selectedDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        // Set up category spinner
        List<String> categories = getCategoryList();
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String amountStr = editTextAmount.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();
                String date = buttonSelectDate.getText().toString();
                String description = editTextDescription.getText().toString();

                if (!amountStr.isEmpty()) {
                    double amount = Double.parseDouble(amountStr);
                    int categoryId = databaseHelper.getCategoryId(category);

                    long result = databaseHelper.addExpense(userId, amount, date, categoryId, description);
                    if (result > 0) {
                        Toast.makeText(ExpenseTrackerActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
                        loadExpenses();
                    } else {
                        Toast.makeText(ExpenseTrackerActivity.this, "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ExpenseTrackerActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<String> getCategoryList() {
        List<String> categories = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllCategories();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    int categoryNameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY_NAME);
                    String categoryName = cursor.getString(categoryNameIndex);
                    categories.add(categoryName);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return categories;
    }

    private void deleteExpense(int position) {
        Expense expense = expenseList.get(position);
        boolean result = databaseHelper.deleteExpense(expense.getId());
        if (result) {
            expenseList.remove(position);
            expenseAdapter.notifyItemRemoved(position);
            loadBudgetInfo();
            Toast.makeText(this, "Expense deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete expense", Toast.LENGTH_SHORT).show();
        }
    }
}
