package com.example.prayer.Pojo;

import java.util.Date;

public class Challenge {

    Date StartDate;

    Date EndDate;

    float progress;

    public Challenge(Date startDate, Date endDate, float progress) {
        StartDate = startDate;
        EndDate = endDate;
        this.progress = progress;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
