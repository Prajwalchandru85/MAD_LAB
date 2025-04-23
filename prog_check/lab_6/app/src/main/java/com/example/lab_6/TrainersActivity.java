package com.example.lab_6;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class TrainersActivity extends AppCompatActivity {

    ArrayList<Trainer> trainerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trainers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trainerList.add(new Trainer(R.drawable.profile, "Alpha"));
        trainerList.add(new Trainer(R.drawable.profile, "Beta"));
        trainerList.add(new Trainer(R.drawable.profile, "Gamma"));

        TrainerAdapter newAdapter = new TrainerAdapter(this, trainerList);
        ListView trainerListView = findViewById(R.id.trainer_list);
        trainerListView.setAdapter(newAdapter);

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