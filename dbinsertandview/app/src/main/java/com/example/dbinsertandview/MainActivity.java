package com.example.dbinsertandview;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtName, edtAge;
    Button btnInsert;
    ListView listView;
    DBHelper dbHelper;
    ArrayList<String> studentList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        btnInsert = findViewById(R.id.btnInsert);
        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        btnInsert.setOnClickListener(v -> {
            String id = edtId.getText().toString();
            String name = edtName.getText().toString();
            String age = edtAge.getText().toString();
            if (dbHelper.insertData(id, name, age)) {
                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                showData();
            } else {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        showData();
    }

    void showData() {
        studentList.clear();
        Cursor cursor = dbHelper.getAllData();
        while (cursor.moveToNext()) {
            String entry = cursor.getString(0) + " | " + cursor.getString(1) + " | " + cursor.getString(2);
            studentList.add(entry);
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }
}
