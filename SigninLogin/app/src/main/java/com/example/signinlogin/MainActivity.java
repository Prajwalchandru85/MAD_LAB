package com.example.signinlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signinlogin.R;

public class MainActivity extends AppCompatActivity {

    EditText signupUsername, signupPassword;
    Button register, gotoLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        register = findViewById(R.id.btn_register);
        gotoLogin = findViewById(R.id.btn_goto_login);
        db = new DBHelper(this);

        register.setOnClickListener(v -> {
            String user = signupUsername.getText().toString();
            String pass = signupPassword.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (db.userExists(user)) {
                    Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserted = db.insertUser(user, pass);
                    if (inserted) {
                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        gotoLogin.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }
}
