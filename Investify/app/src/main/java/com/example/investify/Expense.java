package com.example.investify;

public class Expense {
    private int id;
    private double amount;
    private String date;
    private int categoryId;
    private String description;
    private String categoryName;

    public Expense(int id, double amount, String date, int categoryId, String description, String categoryName) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
        this.description = description;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
