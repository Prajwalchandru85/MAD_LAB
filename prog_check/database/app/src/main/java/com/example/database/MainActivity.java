package com.example.database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);     // Register
        Button button2 = findViewById(R.id.button2);   // Login or future use
        EditText username = findViewById(R.id.username);
        EditText mail = findViewById(R.id.mail);
        EditText pass = findViewById(R.id.pass);

        db = new DBhelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if (user.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserted = db.insert(user, password, email);

                    if (inserted) {
                        Toast.makeText(MainActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Error inserting data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Optional: Handle button2 for login later

    }
}
