package com.suzei.timescheduler.database;

import android.arch.lifecycle.LiveData;

import com.suzei.timescheduler.database.Schedule;
import com.suzei.timescheduler.database.ScheduleDao;

import java.util.List;

public class ScheduleRepository {

    private ScheduleDao scheduleDao;

    public void createSchedule(Schedule schedule) {
        scheduleDao.createSchedule(schedule);
    }

    public LiveData<List<Schedule>> getAllSchedule() {
        return scheduleDao.getAllSchedule();
    }

    public LiveData<Schedule> getScheduleById(String itemId) {
        return scheduleDao.getScheduleById(itemId);
    }

    public void updateSchedule(Schedule schedule) {
        scheduleDao.updateSchedule(schedule);
    }

    public void deleteSchedule(Schedule schedule) {
        scheduleDao.delete(schedule);
    }

    public void deleteAllSchedule() {
        scheduleDao.deleteAllSchedule();
    }

}
