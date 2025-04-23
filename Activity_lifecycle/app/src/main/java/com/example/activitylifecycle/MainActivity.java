package com.example.activitylifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle Event";
    private TextView lifecycleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lifecycleText = findViewById(R.id.lifecycleText);

        String message = "onCreate() called\n";
        lifecycleText.append(message);
        Log.d(TAG, message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String message = "onStart() called\n";
        lifecycleText.append(message);
        Log.d(TAG, message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String message = "onResume() called\n";
        lifecycleText.append(message);
        Log.d(TAG, message);
    }
}
