package com.example.signinlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginBtn;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginusername);
        loginPassword = findViewById(R.id.loginpassword);
        loginBtn = findViewById(R.id.btnlogin);
        db = new DBHelper(this);

        loginBtn.setOnClickListener(v -> {
            String user = loginUsername.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUser(user, pass)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, Booking.class);
                i.putExtra("username", user); // passing user to next screen
                startActivity(i);
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
