package com.example.q2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuIcon = findViewById(R.id.menuIcon);

        menuIcon.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_items, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.image1) {  // Updated to match menu_items.xml
                menuIcon.setImageResource(R.drawable.ic1);
                Toast.makeText(this, "Image -1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.image2) {  // Updated to match menu_items.xml
                menuIcon.setImageResource(R.drawable.ic2);
                Toast.makeText(this, "Image -2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            }

            return false;
        });

        popupMenu.show();
    }
}
