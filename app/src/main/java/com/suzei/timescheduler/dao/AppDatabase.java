package com.suzei.timescheduler.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.suzei.timescheduler.model.Schedule;

@Database(entities = {Schedule.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ScheduleDao getScheduleDao();

}
