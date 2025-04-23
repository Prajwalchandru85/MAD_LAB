package com.example.alldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {

        super(context, "StudentDB", null, 1);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student(id INTEGER PRIMARY KEY, name TEXT, age INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public boolean insert(int id, String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("age", age);
        long result = db.insert("student", null, cv);
        return result != -1;
    }

    public boolean update(int id, String name, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("age", age);
        int result = db.update("student", cv, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("student", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
