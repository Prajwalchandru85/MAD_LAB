package com.example.popupmenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.one) {
                            Toast.makeText(MainActivity.this, "one", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.two) {
                            Toast.makeText(MainActivity.this, "two", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.three) {
                            Toast.makeText(MainActivity.this, "three", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            return false;
                        }

                    }
                });


                popupMenu.show();
            }
        });
    }
}
