package com.example.prayer.Pojo;

public class Pray {

    private String  Time;
    private float progress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String  name;



    public Pray(String time, float progress, String name) {
        Time = time;

        this.progress = progress;
        this.name = name;
    }


    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}

