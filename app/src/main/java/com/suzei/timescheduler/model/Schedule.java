package com.suzei.timescheduler.model;

public class Schedule {

    private long _id;
    private String name, time;
    private int active;
    private Day day;

    public Schedule(long _id, String name, String time, int active, Day day) {
        this._id = _id;
        this.name = name;
        this.time = time;
        this.active = active;
        this.day = day;
    }

    public Schedule(String name, String time, int active, Day day) {
        this.name = name;
        this.time = time;
        this.active = active;
        this.day = day;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
