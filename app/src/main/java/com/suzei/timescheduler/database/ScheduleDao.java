package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(Schedule schedule);

    @Query("SELECT * FROM Schedule")
    LiveData<List<Schedule>> getAllSchedule();

    @Query("SELECT * FROM Schedule WHERE id = :itemId")
    LiveData<Schedule> getScheduleById(String itemId);

    @Delete
    void delete(Schedule schedule);

    @Query("DELETE FROM Schedule")
    void deleteAllSchedule();

}
