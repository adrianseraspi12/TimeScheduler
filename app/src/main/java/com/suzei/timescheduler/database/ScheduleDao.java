package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchedule(ScheduleEntity scheduleEntity);

    @Query("SELECT * FROM ScheduleEntity")
    LiveData<List<ScheduleEntity>> getAllSchedule();

    @Query("SELECT * FROM ScheduleEntity WHERE id = :itemId")
    LiveData<ScheduleEntity> getScheduleById(String itemId);

    @Delete
    void delete(ScheduleEntity scheduleEntity);

    @Query("DELETE FROM ScheduleEntity")
    void deleteAllSchedule();

}
