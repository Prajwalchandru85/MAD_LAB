package com.example.myapplication;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AppDetailActivity extends AppCompatActivity {
    private TextView detailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        detailsTextView = findViewById(R.id.detailsTextView);
        String packageName = getIntent().getStringExtra("packageName");
        displayAppDetails(packageName);
    }

    private void displayAppDetails(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            String details = "Package: " + packageName + "\n" +
                    "Version: " + packageInfo.versionName + "\n" +
                    "Permissions: \n";
            if (packageInfo.requestedPermissions != null) {
                for (String perm : packageInfo.requestedPermissions) {
                    details += perm + "\n";
                }
            } else {
                details += "No special permissions required.";
            }
            detailsTextView.setText(details);
        } catch (Exception e) {
            detailsTextView.setText("Failed to load details.");
        }
    }
}
