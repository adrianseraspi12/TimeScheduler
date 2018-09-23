package com.suzei.timescheduler.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Schedule {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String startTime;

    private String endTime;

    private int active;

    public Schedule(String name, String startTime, String endTime, int active) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
