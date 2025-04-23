package com.example.lab_6;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MembershipActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final String[] membershipList = {"1month - Rs.500", "3months - Rs. 1300", "6months - Rs. 2700", "12months - 5000"};
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Toast.makeText(this, membershipList[pos], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_membership);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.membership_spinner);
        ArrayAdapter<String> newAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, membershipList);
        spinner.setAdapter(newAdapter);
        spinner.setOnItemSelectedListener(this);

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