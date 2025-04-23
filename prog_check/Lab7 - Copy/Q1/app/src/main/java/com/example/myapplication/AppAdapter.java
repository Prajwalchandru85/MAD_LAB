package com.example.myapplication;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AppAdapter extends ArrayAdapter<ApplicationInfo> {
    private Context context;
    private List<ApplicationInfo> appList;
    private PackageManager packageManager;

    public AppAdapter(Context context, List<ApplicationInfo> appList) {
        super(context, R.layout.item_app, appList);
        this.context = context;
        this.appList = appList;
        this.packageManager = context.getPackageManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        }

        ApplicationInfo appInfo = appList.get(position);

        ImageView appIcon = convertView.findViewById(R.id.appIcon);
        TextView appName = convertView.findViewById(R.id.appName);

        appName.setText(appInfo.loadLabel(packageManager));
        appIcon.setImageDrawable(appInfo.loadIcon(packageManager));

        return convertView;
    }
}
