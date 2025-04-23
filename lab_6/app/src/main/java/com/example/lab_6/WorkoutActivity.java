package com.example.lab_6;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    ArrayList<String> workout_plans_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workout_plans_list.add("Cardio");
        workout_plans_list.add("Push-ups");
        workout_plans_list.add("Pull-ups");
        workout_plans_list.add("Squats");
        workout_plans_list.add("Deadlifts");
        workout_plans_list.add("Lunges");


        ListView workout_plans = findViewById(R.id.workout_plans);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, workout_plans_list);
        workout_plans.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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