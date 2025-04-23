package com.example.q3;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textViewContent;
    private String originalText = "Digital Transformation is the process of integrating digital technology into all areas of business. It results in fundamental changes to how businesses operate and deliver value to customers.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewContent = findViewById(R.id.textViewContent);
        textViewContent.setText(originalText);

        Button btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this::showFilterMenu);
    }

    private void showFilterMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_filter, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.search) {
                showSearchDialog();
                return true;
            } else if (id == R.id.highlight) {
                showHighlightDialog();
                return true;
            } else if (id == R.id.sort) {
                sortContent();
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter keyword to search");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Search", (dialog, which) -> {
            String keyword = input.getText().toString();
            if (originalText.contains(keyword)) {
                Toast.makeText(this, "Keyword found!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Keyword not found!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showHighlightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter text to highlight");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Highlight", (dialog, which) -> {
            String keyword = input.getText().toString();
            highlightText(keyword);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void highlightText(String keyword) {
        SpannableString spannableString = new SpannableString(originalText);
        int index = originalText.indexOf(keyword);

        while (index >= 0) {
            spannableString.setSpan(new BackgroundColorSpan(0xFFFFFF00), index, index + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = originalText.indexOf(keyword, index + 1);
        }

        textViewContent.setText(spannableString);
    }

    private void sortContent() {
        List<String> words = Arrays.asList(originalText.split(" "));
        Collections.sort(words);
        textViewContent.setText(String.join(" ", words));
    }
}
