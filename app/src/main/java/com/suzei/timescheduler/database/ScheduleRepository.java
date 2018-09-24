package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class ScheduleRepository {

    private ScheduleDao scheduleDao;

    public ScheduleRepository(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public void createSchedule(Schedule schedule) {
        scheduleDao.insertSchedule(schedule);
    }

    public LiveData<List<Schedule>> getAllSchedule() {
        return scheduleDao.getAllSchedule();
    }

    public LiveData<Schedule> getScheduleById(String itemId) {
        return scheduleDao.getScheduleById(itemId);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    public void deleteAllSchedule() {
        scheduleDao.deleteAllSchedule();
    }

}
