package com.suzei.timescheduler.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.suzei.timescheduler.model.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert
    void insert(Schedule... schedule);

    @Update
    void update(Schedule... schedule);

    @Delete
    void delete(Schedule schedule);

    @Query("SELECT * FROM schedule")
    List<Schedule> getAllSchedule();

    @Delete
    void deleteAll(Schedule... schedules);

}
