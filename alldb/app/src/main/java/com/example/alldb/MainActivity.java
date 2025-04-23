package com.example.alldb;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText idEdit, nameEdit, ageEdit;
    Button insertBtn, updateBtn, deleteBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idEdit = findViewById(R.id.idEdit);
        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        insertBtn = findViewById(R.id.insertBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        db = new DBHelper(this);

        insertBtn.setOnClickListener(v -> {
            int id = Integer.parseInt(idEdit.getText().toString());
            String name = nameEdit.getText().toString();
            int age = Integer.parseInt(ageEdit.getText().toString());

            boolean result = db.insert(id, name, age);
            showToast(result ? "Inserted Successfully" : "Insert Failed");
        });

        updateBtn.setOnClickListener(v -> {
            int id = Integer.parseInt(idEdit.getText().toString());
            String name = nameEdit.getText().toString();
            int age = Integer.parseInt(ageEdit.getText().toString());

            boolean result = db.update(id, name, age);
            showToast(result ? "Updated Successfully" : "Update Failed");
        });

        deleteBtn.setOnClickListener(v -> {
            int id = Integer.parseInt(idEdit.getText().toString());
            boolean result = db.delete(id);
            showToast(result ? "Deleted Successfully" : "Delete Failed");
        });
    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
