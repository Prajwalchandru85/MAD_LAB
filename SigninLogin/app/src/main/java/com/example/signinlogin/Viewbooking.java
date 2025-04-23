package com.example.signinlogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import java.util.ArrayList;

public class Viewbooking extends AppCompatActivity {

    DBHelper dbHelper;
    ListView lv;
    ArrayList<String> bookingsList;
    ArrayList<Integer> bookingIds;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewbooking);

        lv = findViewById(R.id.lv);
        dbHelper = new DBHelper(this);
        registerForContextMenu(lv);

        loadBookings();
    }

    private void loadBookings() {
        bookingsList = new ArrayList<>();
        bookingIds = new ArrayList<>();

        Cursor cursor = dbHelper.getAllBookings();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No bookings found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String place = cursor.getString(1);
                String date = cursor.getString(2);
                String transport = cursor.getString(3);

                String booking = "Place: " + place + "\nDate: " + date + "\nTransport: " + transport;
                bookingsList.add(booking);
                bookingIds.add(id);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingsList);
            lv.setAdapter(adapter);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inf = new MenuInflater(this);
        inf.inflate(R.menu.context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;
        int bookingId = bookingIds.get(pos);
        int id=item.getItemId();

            if (id==R.id.edit) {
                showEditDialog(bookingId, pos);
                return true;
            }
            else if (id==R.id.delete) {
                dbHelper.deleteBooking(bookingId);
                loadBookings();
                Toast.makeText(this, "Booking deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showEditDialog(int bookingId, int pos) {
        View view = getLayoutInflater().inflate(R.layout.edit_dialog, null);
        EditText etPlace = view.findViewById(R.id.etPlace);
        EditText etDate = view.findViewById(R.id.etDate);
        EditText etTransport = view.findViewById(R.id.etTransport);

        String[] parts = bookingsList.get(pos).split("\n");
        etPlace.setText(parts[0].replace("Place: ", ""));
        etDate.setText(parts[1].replace("Date: ", ""));
        etTransport.setText(parts[2].replace("Transport: ", ""));

        new AlertDialog.Builder(this)
                .setTitle("Edit Booking")
                .setView(view)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newPlace = etPlace.getText().toString();
                    String newDate = etDate.getText().toString();
                    String newTransport = etTransport.getText().toString();

                    if (dbHelper.updateBooking(bookingId, newPlace, newDate, newTransport)) {
                        Toast.makeText(this, "Booking updated", Toast.LENGTH_SHORT).show();
                        loadBookings();
                    } else {
                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
