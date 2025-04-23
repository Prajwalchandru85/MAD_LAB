package com.example.investify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "investify.db";
    private static final int DATABASE_VERSION = 1;

    // User table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PASSWORD = "password";

    // Income table
    public static final String TABLE_INCOME = "income";
    public static final String COLUMN_INCOME_ID = "income_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_MONTHLY_INCOME = "monthly_income";
    public static final String COLUMN_MONTHLY_BUDGET = "monthly_budget";

    // Expense categories table
    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_CATEGORY_NAME = "category_name";

    // Expenses table
    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_EXPENSE_ID = "expense_id";
    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_DATE = "date";
    public static final String COLUMN_EXPENSE_CATEGORY = "category_id";
    public static final String COLUMN_EXPENSE_DESCRIPTION = "description";

    // Create table statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, "
            + COLUMN_PHONE + " TEXT NOT NULL, "
            + COLUMN_AGE + " INTEGER NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL"
            + ")";

    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME + "("
            + COLUMN_INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_MONTHLY_INCOME + " REAL NOT NULL, "
            + COLUMN_MONTHLY_BUDGET + " REAL NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY_NAME + " TEXT NOT NULL UNIQUE"
            + ")";

    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES + "("
            + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_EXPENSE_AMOUNT + " REAL NOT NULL, "
            + COLUMN_EXPENSE_DATE + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_CATEGORY + " INTEGER NOT NULL, "
            + COLUMN_EXPENSE_DESCRIPTION + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "), "
            + "FOREIGN KEY(" + COLUMN_EXPENSE_CATEGORY + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_EXPENSES);

        // Insert default expense categories
        insertDefaultCategories(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertDefaultCategories(SQLiteDatabase db) {
        String[] defaultCategories = {"Groceries", "Shopping", "Transportation", "Petrol", "Entertainment",
                "Dining", "Utilities", "Rent", "Healthcare", "Education",
                "Stationary", "Milk", "House Products", "Party", "Other"};

        for (String category : defaultCategories) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY_NAME, category);
            db.insert(TABLE_CATEGORIES, null, values);
        }
    }

    // Add a new user
    public long addUser(String name, String email, String phone, int age, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // Check if user exists with given email and password
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Check if email already exists
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    // Get user details by email
    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_AGE};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        return db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    // Get user ID by email
    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int userId = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            userId = cursor.getInt(columnIndex);
        }
        cursor.close();
        return userId;
    }

    // Save or update user income and budget
    public long saveIncome(int userId, double monthlyIncome, double monthlyBudget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_MONTHLY_INCOME, monthlyIncome);
        values.put(COLUMN_MONTHLY_BUDGET, monthlyBudget);

        // Check if record exists
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(TABLE_INCOME, null, selection, selectionArgs, null, null, null);

        long id;
        if (cursor.getCount() > 0) {
            // Update existing record
            id = db.update(TABLE_INCOME, values, selection, selectionArgs);
        } else {
            // Insert new record
            id = db.insert(TABLE_INCOME, null, values);
        }

        cursor.close();
        db.close();
        return id;
    }

    // Get user income and budget
    public Cursor getUserIncome(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        return db.query(TABLE_INCOME, null, selection, selectionArgs, null, null, null);
    }

    // Add a new expense
    public long addExpense(int userId, double amount, String date, int categoryId, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_EXPENSE_AMOUNT, amount);
        values.put(COLUMN_EXPENSE_DATE, date);
        values.put(COLUMN_EXPENSE_CATEGORY, categoryId);
        values.put(COLUMN_EXPENSE_DESCRIPTION, description);

        long id = db.insert(TABLE_EXPENSES, null, values);
        db.close();
        return id;
    }

    // Delete an expense
    public boolean deleteExpense(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EXPENSES, COLUMN_EXPENSE_ID + " = ?",
                new String[]{String.valueOf(expenseId)}) > 0;
    }

    // Get all expenses for a user
    public Cursor getAllExpenses(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT e.*, c." + COLUMN_CATEGORY_NAME +
                " FROM " + TABLE_EXPENSES + " e JOIN " + TABLE_CATEGORIES +
                " c ON e." + COLUMN_EXPENSE_CATEGORY + " = c." + COLUMN_CATEGORY_ID +
                " WHERE e." + COLUMN_USER_ID + " = ?" +
                " ORDER BY e." + COLUMN_EXPENSE_DATE + " DESC";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

    // Get all expense categories
    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CATEGORIES, null, null, null, null, null, COLUMN_CATEGORY_NAME);
    }

    // Get category by ID
    public String getCategoryName(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CATEGORY_NAME};
        String selection = COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        Cursor cursor = db.query(TABLE_CATEGORIES, columns, selection, selectionArgs, null, null, null);

        String categoryName = "";
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME);
            categoryName = cursor.getString(columnIndex);
        }
        cursor.close();
        return categoryName;
    }

    // Get category ID by name
    public int getCategoryId(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CATEGORY_ID};
        String selection = COLUMN_CATEGORY_NAME + " = ?";
        String[] selectionArgs = {categoryName};
        Cursor cursor = db.query(TABLE_CATEGORIES, columns, selection, selectionArgs, null, null, null);

        int categoryId = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID);
            categoryId = cursor.getInt(columnIndex);
        }
        cursor.close();
        return categoryId;
    }

    // Get expense summary by category for a user
    public Cursor getExpenseSummaryByCategory(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c." + COLUMN_CATEGORY_NAME + ", SUM(e." + COLUMN_EXPENSE_AMOUNT + ") as total " +
                "FROM " + TABLE_EXPENSES + " e JOIN " + TABLE_CATEGORIES +
                " c ON e." + COLUMN_EXPENSE_CATEGORY + " = c." + COLUMN_CATEGORY_ID +
                " WHERE e." + COLUMN_USER_ID + " = ?" +
                " GROUP BY e." + COLUMN_EXPENSE_CATEGORY +
                " ORDER BY total DESC";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }
}
