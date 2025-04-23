package com.example.myapplication;
// MainActivity.java
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private AppAdapter adapter;
    private List<ApplicationInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        appList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        adapter = new AppAdapter(this, appList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAppOptions(appList.get(position));
                return true;
            }
        });
    }

    private void showAppOptions(ApplicationInfo appInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(appInfo.loadLabel(getPackageManager()))
                .setItems(new CharSequence[]{"Check Type", "Open", "Uninstall", "View Details"}, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            checkAppType(appInfo);
                            break;
                        case 1:
                            openApp(appInfo);
                            break;
                        case 2:
                            uninstallApp(appInfo);
                            break;
                        case 3:
                            viewAppDetails(appInfo);
                            break;
                    }
                }).show();
    }

    private void checkAppType(ApplicationInfo appInfo) {
        boolean isSystemApp = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        Toast.makeText(this, isSystemApp ? "System App" : "User Installed App", Toast.LENGTH_SHORT).show();
    }

    private void openApp(ApplicationInfo appInfo) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(this, "Cannot open this app", Toast.LENGTH_SHORT).show();
        }
    }

    private void uninstallApp(ApplicationInfo appInfo) {

            // Show confirmation dialog
            new AlertDialog.Builder(this)
                    .setTitle("Uninstall App")
                    .setMessage("Are you sure you want to uninstall " + appInfo.loadLabel(getPackageManager()) + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:" + appInfo.packageName));
                        startActivity(intent);
                    })
                    .setNegativeButton("No", null) // Dismiss if "No" is clicked
                    .show();
    }


    private void viewAppDetails(ApplicationInfo appInfo) {
        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra("packageName", appInfo.packageName);
        startActivity(intent);
    }
}
