package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String dbname = "login.db";
    public static final String tablename = "users";
    public static final String col1 = "username";
    public static final String col2 = "pass";
    public static final String col3 = "mail";

    public DBhelper(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + tablename + "(" +
                col1 + " TEXT PRIMARY KEY, " +
                col3 + " TEXT, " +
                col2 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }

    public boolean insert(String username, String pass, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col1, username);
        cv.put(col2, pass);
        cv.put(col3, mail);
        long result = db.insert(tablename, null, cv);
        return result != -1;
    }

    public boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE " + col1 + " = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public boolean checkuserpass(String username, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE " + col1 + " = ? AND " + col2 + " = ?", new String[]{username, pass});
        return cursor.getCount() > 0;
    }
}
