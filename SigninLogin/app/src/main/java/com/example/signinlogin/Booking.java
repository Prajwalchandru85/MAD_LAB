package com.example.signinlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Booking extends AppCompatActivity {

    EditText etPlace;
    Spinner spTransport;
    DatePicker datePicker;
    Button btnBook;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);

        etPlace = findViewById(R.id.et1);
        spTransport = findViewById(R.id.spinner);
        datePicker = findViewById(R.id.dp);
        btnBook = findViewById(R.id.button);
        db = new DBHelper(this);

        // Populate spinner
        String[] bookings = {"Car", "Bike", "Flight", "Train", "Ship"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransport.setAdapter(adapter);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = etPlace.getText().toString().trim();
                String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                String transport = spTransport.getSelectedItem().toString();

                if (place.isEmpty()) {
                    Toast.makeText(Booking.this, "Please enter a place", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = db.insertBooking(place, date, transport);

                if (success) {
                    Toast.makeText(Booking.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Booking.this, Viewbooking.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Booking.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
