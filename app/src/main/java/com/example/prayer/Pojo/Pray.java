package com.example.prayer.Pojo;

public class Pray {

    private String  Name;
    private String  Time;
    private int BackgroundCOLOR;
    private int TextCOLOR;
    private float progress;



    public Pray(String name, String time, int color, int textCOLOR, float progress) {
        Name = name;
        Time = time;
        BackgroundCOLOR = color;
        TextCOLOR = textCOLOR;
        this.progress = progress;
    }

    public int getBackgroundCOLOR() {
        return BackgroundCOLOR;
    }


    public int getTextCOLOR() {
        return TextCOLOR;
    }

    public void setTextCOLOR(int textCOLOR) {
        TextCOLOR = textCOLOR;
    }

    public void setBackgroundCOLOR(int backgroundCOLOR) {
        this.BackgroundCOLOR = backgroundCOLOR;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

