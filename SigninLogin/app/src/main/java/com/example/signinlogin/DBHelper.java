package com.example.signinlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "Userdata.db";
    private static final int DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(username TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS booking(id INTEGER PRIMARY KEY AUTOINCREMENT, place TEXT NOT NULL, date TEXT NOT NULL, transport TEXT NOT NULL)");
        Log.d(TAG, "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS booking");
        onCreate(db);
    }

    // User methods
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // Booking methods
    public boolean insertBooking(String place, String date, String transport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("date", date);
        values.put("transport", transport);
        return db.insert("booking", null, values) != -1;
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM booking", null);
    }

    public boolean updateBooking(int id, String place, String date, String transport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("date", date);
        values.put("transport", transport);
        return db.update("booking", values, "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteBooking(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("booking", "id=?", new String[]{String.valueOf(id)}) > 0;
    }
}
