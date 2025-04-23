package com.example.lab_6;

public class Trainer {
    private int trainer_image_id;
    private String trainer_name;

    public Trainer(int tid, String tname){
        trainer_name = tname;
        trainer_image_id = tid;
    }

    public int getTrainer_image_id() {
        return trainer_image_id;
    }

    public String getTrainer_name() {
        return trainer_name;
    }
}
