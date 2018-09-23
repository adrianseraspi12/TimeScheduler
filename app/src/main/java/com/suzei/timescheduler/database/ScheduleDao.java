package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert
    void createSchedule(Schedule schedule);

    @Query("SELECT * FROM schedule")
    LiveData<List<Schedule>> getAllSchedule();

    @Query("SELECT * FROM schedule WHERE id = :itemId")
    LiveData<Schedule> getScheduleById(String itemId);

    @Update
    void updateSchedule(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

    @Query("DELETE FROM schedule")
    void deleteAllSchedule();

}
