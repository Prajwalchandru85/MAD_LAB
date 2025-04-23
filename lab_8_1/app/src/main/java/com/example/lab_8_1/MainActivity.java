package com.example.lab_8_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_8_1.adapters.TaskAdapter;
import com.example.lab_8_1.db.DatabaseHelper;
import com.example.lab_8_1.models.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        tasks = new ArrayList<>();

        setupToolbar();
        setupRecyclerView();
        setupFab();
        loadTasks();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        RecyclerView taskList = findViewById(R.id.taskList);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(tasks, this::showTaskOptions);
        taskList.setAdapter(taskAdapter);
    }

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.addTaskFab);
        fab.setOnClickListener(v -> showTaskDialog(null));
    }

    private void loadTasks() {
        tasks.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TASKS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String dueDate = cursor.getString(cursor.getColumnIndexOrThrow("due_date"));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
            tasks.add(new Task(id, name, dueDate, priority));
        }
        cursor.close();
        taskAdapter.notifyDataSetChanged();
    }

    private void showTaskDialog(Task task) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_task, null);
        TextInputEditText nameInput = dialogView.findViewById(R.id.taskNameInput);
        TextInputEditText dateInput = dialogView.findViewById(R.id.dueDateInput);
        AutoCompleteTextView priorityDropdown = dialogView.findViewById(R.id.priorityDropdown);

        // Setup priority dropdown
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.priorities, android.R.layout.simple_dropdown_item_1line);
        priorityDropdown.setAdapter(adapter);

        // Setup date picker
        dateInput.setOnClickListener(v -> showDatePicker(dateInput));

        // Pre-fill data if editing
        if (task != null) {
            nameInput.setText(task.getName());
            dateInput.setText(task.getDueDate());
            priorityDropdown.setText(task.getPriority(), false);
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle(task == null ? "Add Task" : "Edit Task")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> saveTask(task,
                        Objects.requireNonNull(nameInput.getText()).toString(),
                        Objects.requireNonNull(dateInput.getText()).toString(),
                        priorityDropdown.getText().toString()))
                .setNegativeButton("Cancel", null);

        builder.show();
    }

    private void showDatePicker(TextInputEditText dateInput) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select due date")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateInput.setText(sdf.format(new Date(selection)));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void saveTask(Task task, String name, String dueDate, String priority) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("due_date", dueDate);
        values.put("priority", priority);

        if (task == null) {
            db.insert(DatabaseHelper.TABLE_TASKS, null, values);
        } else {
            db.update(DatabaseHelper.TABLE_TASKS, values, "id = ?",
                    new String[]{String.valueOf(task.getId())});
        }

        loadTasks();
    }

    private void showTaskOptions(Task task) {
        String[] options = {"Edit", "Delete"};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Task Options")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showTaskDialog(task);
                    } else {
                        deleteTask(task);
                    }
                })
                .show();
    }

    private void deleteTask(Task task) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete(DatabaseHelper.TABLE_TASKS, "id = ?",
                            new String[]{String.valueOf(task.getId())});
                    loadTasks();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}