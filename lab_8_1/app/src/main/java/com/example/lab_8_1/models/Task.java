package com.example.lab_8_1.models;

public class Task {
    private final int id;
    private final String name;
    private final String dueDate;
    private final String priority;

    public Task(int id, String name, String dueDate, String priority) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
}
