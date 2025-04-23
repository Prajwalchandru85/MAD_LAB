package com.example.lab_8_2;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_8_2.adapters.GroceryItemAdapter;
import com.example.lab_8_2.db.DatabaseHelper;
import com.example.lab_8_2.models.GroceryItem;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText itemNameInput, itemCostInput;
    private ListView itemListView;
    private Spinner itemSpinner;
    private TextView totalCostView;
    private List<Pair<GroceryItem, Boolean>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        itemNameInput = findViewById(R.id.itemNameInput);
        itemCostInput = findViewById(R.id.itemCostInput);
        itemListView = findViewById(R.id.itemListView);
        totalCostView = findViewById(R.id.totalCostView);
        itemSpinner = findViewById(R.id.itemSpinner);

        MaterialButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> addItem());

        loadItems();
        setupListView();
        setupSpinner();
    }

    private void addItem() {
        String name = itemNameInput.getText().toString().trim();
        String costStr = itemCostInput.getText().toString().trim();

        if (name.isEmpty() || costStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double cost = Double.parseDouble(costStr);
            GroceryItem item = new GroceryItem(name, cost);
            dbHelper.addItem(item);

            itemNameInput.getText().clear();
            itemCostInput.getText().clear();

            loadItems();
            setupListView();
            setupSpinner();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid cost value", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadItems() {
        items = dbHelper.getAllItems().stream()
                .map(item -> new Pair<>(item, false))
                .collect(Collectors.toList());
    }

    private void setupListView() {
        GroceryItemAdapter adapter = new GroceryItemAdapter(this, items);
        itemListView.setAdapter(adapter);

        adapter.setOnItemCheckedListener((position, isChecked) -> {
            items.set(position, new Pair<>(items.get(position).first, isChecked));
            updateTotalCost();
        });
    }

    private void setupSpinner() {
        List<String> itemNames = items.stream()
                .map(item -> item.first.getName())
                .collect(Collectors.toList());
        itemSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemNames));
    }

    private void updateTotalCost() {
        double totalCost = items.stream()
                .filter(item -> item.second)
                .mapToDouble(item -> item.first.getCost())
                .sum();
        totalCostView.setText(String.format(Locale.US, "Total cost: $%.2f", totalCost));
    }
}