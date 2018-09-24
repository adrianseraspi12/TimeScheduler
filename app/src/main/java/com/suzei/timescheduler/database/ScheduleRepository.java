package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class ScheduleRepository {

    private ScheduleDao scheduleDao;

    public ScheduleRepository(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public void createSchedule(ScheduleEntity scheduleEntity) {
        scheduleDao.insertSchedule(scheduleEntity);
    }

    public LiveData<List<ScheduleEntity>> getAllSchedule() {
        return scheduleDao.getAllSchedule();
    }

    public LiveData<ScheduleEntity> getScheduleById(String itemId) {
        return scheduleDao.getScheduleById(itemId);
    }

    public void deleteSchedule(ScheduleEntity scheduleEntity) {
        scheduleDao.delete(scheduleEntity);
    }

    public void deleteAllSchedule() {
        scheduleDao.deleteAllSchedule();
    }

}
