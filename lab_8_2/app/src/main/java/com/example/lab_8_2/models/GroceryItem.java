package com.example.lab_8_2.models;

public class GroceryItem {
    private int id;
    private String name;
    private double cost;

    public GroceryItem(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}
