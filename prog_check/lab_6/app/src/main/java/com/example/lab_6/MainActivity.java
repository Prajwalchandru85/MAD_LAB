package com.example.lab_6;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.


        if (item.getItemId()==R.id.workout){
            Intent intent = new Intent(this, WorkoutActivity.class);
            try{
                startActivity(intent);
            }catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Could not find Workout Activity!", Toast.LENGTH_LONG).show();
            }
        }


        if (item.getItemId()==R.id.trainers){
            Intent intent = new Intent(this, TrainersActivity.class);
            try{
                startActivity(intent);
            }catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Could not find Trainer Activity!", Toast.LENGTH_LONG).show();
            }
        }

        if (item.getItemId()==R.id.membership){
            Intent intent = new Intent(this, MembershipActivity.class);
            try{
                startActivity(intent);
            }catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Could not find Membership Activity!", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}