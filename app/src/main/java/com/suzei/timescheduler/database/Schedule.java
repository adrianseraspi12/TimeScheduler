package com.suzei.timescheduler.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Random;
import java.util.UUID;

@Entity
public class Schedule {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;

    @ColumnInfo(name = "start_time")
    private String startTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

    private int active;

    @Ignore
    public Schedule(String name, String startTime, String endTime, int active) {
        this.id = generateRandomId();
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public Schedule(@NonNull String id, String name, String startTime, String endTime, int active) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    private String generateRandomId() {
        Random r = new Random( System.currentTimeMillis() );
        int id = 10000 + r.nextInt(20000);
        return String.valueOf(id);
    }

}
